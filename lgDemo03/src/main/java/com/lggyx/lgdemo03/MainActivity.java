package com.lggyx.lgdemo03;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView tvResult;
    private String firstNum = "";
    private String secondNum = "";
    private String operator = "";
    private String result = "";
    private String showText = "0";
    private boolean hasResult = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvResult = findViewById(R.id.tv_result);
        tvResult.setText(showText);

        int[] ids = {
                R.id.btn_ce, R.id.btn_divide, R.id.btn_mul, R.id.btn_c,
                R.id.btn_7, R.id.btn_8, R.id.btn_9, R.id.btn_add,
                R.id.btn_4, R.id.btn_5, R.id.btn_6, R.id.btn_sub,
                R.id.btn_1, R.id.btn_2, R.id.btn_3, R.id.btn_reciprocal,
                R.id.btn_0, R.id.btn_dot, R.id.btn_eq,
                R.id.ib_sqrt
        };

        for (int id : ids) {
            findViewById(id).setOnClickListener(this);
        }
    }

    @Override
    public void onClick(View v) {
        if (!verify(v)) {
            return;
        }

        int id = v.getId();

        if (id == R.id.btn_ce) {
            if (operator.isEmpty()) {
                firstNum = firstNum.isEmpty() ? "" : firstNum.substring(0, firstNum.length() - 1);
                showText = firstNum.isEmpty() ? "0" : firstNum;
            } else {
                secondNum = secondNum.isEmpty() ? "" : secondNum.substring(0, secondNum.length() - 1);
                showText = secondNum.isEmpty() ? (firstNum + " " + operator) : (firstNum + " " + operator + " " + secondNum);
            }
        } else if (id == R.id.btn_c) {
            clear();
            showText = "0";
        } else if (id == R.id.btn_0 || id == R.id.btn_1 || id == R.id.btn_2 || id == R.id.btn_3 ||
                   id == R.id.btn_4 || id == R.id.btn_5 || id == R.id.btn_6 ||
                   id == R.id.btn_7 || id == R.id.btn_8 || id == R.id.btn_9) {
            if (hasResult) {
                clear();
                hasResult = false;
            }
            String num = ((Button) v).getText().toString();
            if (operator.isEmpty()) {
                firstNum += num;
                showText = firstNum;
            } else {
                secondNum += num;
                showText = firstNum + " " + operator + " " + secondNum;
            }
        } else if (id == R.id.btn_dot) {
            if (hasResult) {
                clear();
                hasResult = false;
            }
            if (operator.isEmpty()) {
                if (firstNum.contains(".")) return;
                if (firstNum.isEmpty()) {
                    firstNum = "0";
                }
                firstNum += ".";
                showText = firstNum;
            } else {
                if (secondNum.contains(".")) return;
                if (secondNum.isEmpty()) {
                    secondNum = "0";
                }
                secondNum += ".";
                showText = firstNum + " " + operator + " " + secondNum;
            }
        } else if (id == R.id.btn_add || id == R.id.btn_sub || id == R.id.btn_divide || id == R.id.btn_mul) {
            if (hasResult) {
                hasResult = false;
            }
            if (!operator.isEmpty() && !secondNum.isEmpty()) {
                double res = caculateFour(Double.parseDouble(firstNum), Double.parseDouble(secondNum), operator);
                firstNum = formatNum(res);
                secondNum = "";
            }
            operator = ((Button) v).getText().toString();
            showText = firstNum + " " + operator;
        } else if (id == R.id.btn_eq) {
            if (operator.isEmpty() || secondNum.isEmpty()) return;
            double res = caculateFour(Double.parseDouble(firstNum), Double.parseDouble(secondNum), operator);
            result = formatNum(res);
            showText = result;
            hasResult = true;
        } else if (id == R.id.ib_sqrt) {
            if (firstNum.isEmpty()) return;
            double num = Double.parseDouble(firstNum);
            if (num < 0) {
                Toast.makeText(this, "负数不能开平方", Toast.LENGTH_SHORT).show();
                return;
            }
            result = formatNum(Math.sqrt(num));
            showText = result;
            hasResult = true;
        } else if (id == R.id.btn_reciprocal) {
            if (firstNum.isEmpty()) return;
            double num = Double.parseDouble(firstNum);
            if (num == 0) {
                Toast.makeText(this, "零不能求倒数", Toast.LENGTH_SHORT).show();
                return;
            }
            result = formatNum(1 / num);
            showText = result;
            hasResult = true;
        }

        tvResult.setText(showText);
    }

    private boolean verify(View v) {
        int id = v.getId();

        if (id == R.id.btn_divide) {
            if (operator.isEmpty() && firstNum.isEmpty()) {
                Toast.makeText(this, "缺少操作数", Toast.LENGTH_SHORT).show();
                return false;
            }
            if (!operator.isEmpty() && secondNum.isEmpty()) {
                Toast.makeText(this, "缺少操作数", Toast.LENGTH_SHORT).show();
                return false;
            }
        } else if (id == R.id.btn_eq) {
            if (operator.isEmpty()) {
                Toast.makeText(this, "缺少运算符", Toast.LENGTH_SHORT).show();
                return false;
            }
            if (secondNum.isEmpty()) {
                Toast.makeText(this, "缺少操作数", Toast.LENGTH_SHORT).show();
                return false;
            }
            if (operator.equals("÷") && Double.parseDouble(secondNum) == 0) {
                Toast.makeText(this, "除数不能为零", Toast.LENGTH_SHORT).show();
                return false;
            }
        } else if (id == R.id.ib_sqrt) {
            if (firstNum.isEmpty()) {
                Toast.makeText(this, "缺少操作数", Toast.LENGTH_SHORT).show();
                return false;
            }
        } else if (id == R.id.btn_reciprocal) {
            if (firstNum.isEmpty()) {
                Toast.makeText(this, "缺少操作数", Toast.LENGTH_SHORT).show();
                return false;
            }
            if (Double.parseDouble(firstNum) == 0) {
                Toast.makeText(this, "零不能求倒数", Toast.LENGTH_SHORT).show();
                return false;
            }
        }

        return true;
    }

    private double caculateFour(double a, double b, String op) {
        switch (op) {
            case "+":
                return a + b;
            case "-":
                return a - b;
            case "×":
                return a * b;
            case "÷":
                return a / b;
            default:
                return 0;
        }
    }

    private String formatNum(double num) {
        if (num == Math.floor(num)) {
            return String.valueOf((long) num);
        }
        return String.valueOf(num);
    }

    private void clear() {
        firstNum = "";
        secondNum = "";
        operator = "";
        result = "";
        showText = "0";
    }
}
