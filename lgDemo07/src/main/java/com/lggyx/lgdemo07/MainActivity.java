package com.lggyx.lgdemo07;

import android.Manifest;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private EditText edNm, edPh, edEm;
    private Button btAdd, btBrw;
    private static final int REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edNm = findViewById(R.id.ed_nm);
        edPh = findViewById(R.id.ed_ph);
        edEm = findViewById(R.id.ed_em);
        btAdd = findViewById(R.id.bt_add);
        btBrw = findViewById(R.id.bt_brw);

        btAdd.setOnClickListener(v -> AddPersonClick());
        btBrw.setOnClickListener(v -> LookPresonClick());
    }

    private void AddPersonClick() {
        if (checkPermission()) {
            addContact();
        } else {
            requestPermission();
        }
    }

    private void LookPresonClick() {
        if (checkPermission()) {
            showContacts();
        } else {
            requestPermission();
        }
    }

    private boolean checkPermission() {
        return ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS) == PackageManager.PERMISSION_GRANTED
            && ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_CONTACTS) == PackageManager.PERMISSION_GRANTED;
    }

    private void requestPermission() {
        ActivityCompat.requestPermissions(this,
            new String[]{
                Manifest.permission.READ_CONTACTS,
                Manifest.permission.WRITE_CONTACTS
            },
            REQUEST_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "权限已授予", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "权限被拒绝", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void addContact() {
        String name = edNm.getText().toString().trim();
        String phone = edPh.getText().toString().trim();
        String email = edEm.getText().toString().trim();

        if (name.isEmpty() && phone.isEmpty() && email.isEmpty()) {
            Toast.makeText(this, "请至少输入一项信息", Toast.LENGTH_SHORT).show();
            return;
        }

        ContentResolver resolver = getContentResolver();
        ContentValues values = new ContentValues();

        // Step 1: insert raw contact
        Uri rawContactUri = resolver.insert(ContactsContract.RawContacts.CONTENT_URI, values);
        if (rawContactUri == null) {
            Toast.makeText(this, "插入失败", Toast.LENGTH_SHORT).show();
            return;
        }
        long rawContactId = android.content.ContentUris.parseId(rawContactUri);

        // Step 2: insert name
        if (!name.isEmpty()) {
            ContentValues nameValues = new ContentValues();
            nameValues.put(ContactsContract.Data.RAW_CONTACT_ID, rawContactId);
            nameValues.put(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE);
            nameValues.put(ContactsContract.CommonDataKinds.StructuredName.DISPLAY_NAME, name);
            resolver.insert(ContactsContract.Data.CONTENT_URI, nameValues);
        }

        // Step 3: insert phone
        if (!phone.isEmpty()) {
            ContentValues phoneValues = new ContentValues();
            phoneValues.put(ContactsContract.Data.RAW_CONTACT_ID, rawContactId);
            phoneValues.put(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE);
            phoneValues.put(ContactsContract.CommonDataKinds.Phone.NUMBER, phone);
            phoneValues.put(ContactsContract.CommonDataKinds.Phone.TYPE, ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE);
            resolver.insert(ContactsContract.Data.CONTENT_URI, phoneValues);
        }

        // Step 4: insert email
        if (!email.isEmpty()) {
            ContentValues emailValues = new ContentValues();
            emailValues.put(ContactsContract.Data.RAW_CONTACT_ID, rawContactId);
            emailValues.put(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.Email.CONTENT_ITEM_TYPE);
            emailValues.put(ContactsContract.CommonDataKinds.Email.ADDRESS, email);
            emailValues.put(ContactsContract.CommonDataKinds.Email.TYPE, ContactsContract.CommonDataKinds.Email.TYPE_WORK);
            resolver.insert(ContactsContract.Data.CONTENT_URI, emailValues);
        }

        Toast.makeText(this, "联系人添加成功", Toast.LENGTH_SHORT).show();
        edNm.setText("");
        edPh.setText("");
        edEm.setText("");
    }

    private void showContacts() {
        List<Map<String, String>> data = getData();
        if (data.isEmpty()) {
            Toast.makeText(this, "暂无联系人", Toast.LENGTH_SHORT).show();
            return;
        }

        View resultDialog = getLayoutInflater().inflate(R.layout.activity_result, null);
        ListView lv = resultDialog.findViewById(R.id.lv);

        SimpleAdapter adapter = new SimpleAdapter(
            this,
            data,
            R.layout.listitem,
            new String[]{"name", "phone", "email"},
            new int[]{R.id.contactsname, R.id.contactsphone, R.id.contactsemail}
        );
        lv.setAdapter(adapter);

        new AlertDialog.Builder(this)
            .setTitle(R.string.dialog_title)
            .setView(resultDialog)
            .setPositiveButton(R.string.dialog_ok, null)
            .show();
    }

    private List<Map<String, String>> getData() {
        List<Map<String, String>> list = new ArrayList<>();
        ContentResolver resolver = getContentResolver();

        Cursor cursor = resolver.query(
            ContactsContract.Contacts.CONTENT_URI,
            new String[]{ContactsContract.Contacts._ID, ContactsContract.Contacts.DISPLAY_NAME},
            null, null, null
        );

        if (cursor != null) {
            while (cursor.moveToNext()) {
                long id = cursor.getLong(cursor.getColumnIndexOrThrow(ContactsContract.Contacts._ID));
                String name = cursor.getString(cursor.getColumnIndexOrThrow(ContactsContract.Contacts.DISPLAY_NAME));

                String phone = getPhone(resolver, id);
                String email = getEmail(resolver, id);

                Map<String, String> map = new HashMap<>();
                map.put("name", name);
                map.put("phone", phone);
                map.put("email", email);
                list.add(map);
            }
            cursor.close();
        }
        return list;
    }

    private String getPhone(ContentResolver resolver, long contactId) {
        String phone = "";
        Cursor cursor = resolver.query(
            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
            new String[]{ContactsContract.CommonDataKinds.Phone.NUMBER},
            ContactsContract.CommonDataKinds.Phone.CONTACT_ID + "=?",
            new String[]{String.valueOf(contactId)},
            null
        );
        if (cursor != null) {
            while (cursor.moveToNext()) {
                phone = cursor.getString(cursor.getColumnIndexOrThrow(ContactsContract.CommonDataKinds.Phone.NUMBER));
                break;
            }
            cursor.close();
        }
        return phone;
    }

    private String getEmail(ContentResolver resolver, long contactId) {
        String email = "";
        Cursor cursor = resolver.query(
            ContactsContract.CommonDataKinds.Email.CONTENT_URI,
            new String[]{ContactsContract.CommonDataKinds.Email.ADDRESS},
            ContactsContract.CommonDataKinds.Email.CONTACT_ID + "=?",
            new String[]{String.valueOf(contactId)},
            null
        );
        if (cursor != null) {
            while (cursor.moveToNext()) {
                email = cursor.getString(cursor.getColumnIndexOrThrow(ContactsContract.CommonDataKinds.Email.ADDRESS));
                break;
            }
            cursor.close();
        }
        return email;
    }
}
