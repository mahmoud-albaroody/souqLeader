package com.alef.souqleader.ui.presentation.callLog

import android.Manifest
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.database.Cursor
import android.provider.CallLog
import android.provider.ContactsContract
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import com.alef.souqleader.R
import com.alef.souqleader.ui.presentation.addlead.TextFiledItem

@Composable
fun AddCallLogScreen(
    navController: NavController,
    modifier: Modifier
) {
    val ctx = LocalContext.current
    var caller by remember { mutableStateOf("") }
    var calledNumber by remember { mutableStateOf("") }
    var duration by remember { mutableStateOf("") }
    var notes by remember { mutableStateOf("") }
    var exists by remember { mutableStateOf<Boolean>(false) }

    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            // Permission granted -> Insert into Call Log
            insertCallLog(ctx, calledNumber, duration)
        } else {
            // Permission denied -> Show some message or handle accordingly
        }
    }


    val permissionLauncher1 = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            isPhoneNumberInContacts(ctx, calledNumber)
        } else {
            exists = false
        // or show message that permission denied
        }
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
    ) {


        TextFiledItem(text = stringResource(R.string.caller_number), click = true) {
            caller = it
        }


        TextFiledItem(stringResource(R.string.caller_called_number), true) {
            calledNumber = it
        }


        TextFiledItem(stringResource(R.string.duration_seconds), true) {
            duration = it
        }



        TextFiledItem(stringResource(R.string.note), true) {
            notes = it
        }



        Button(modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 40.dp, horizontal = 20.dp),
            shape = RoundedCornerShape(15.dp),
            colors = ButtonDefaults.buttonColors(colorResource(id = R.color.blue)),
            onClick = {
                when (PackageManager.PERMISSION_GRANTED) {
                    ContextCompat.checkSelfPermission(ctx, Manifest.permission.WRITE_CALL_LOG) -> {
                                addContact(
                                    ctx,
                                    calledNumber, caller
                                )
                            insertCallLog(ctx, calledNumber, duration) // Add call log
                    }

                    else -> {
                        permissionLauncher.launch(Manifest.permission.WRITE_CALL_LOG)
                    }
                }
            }) {
            Text(
                text = stringResource(R.string.submit),
                modifier = Modifier.padding(vertical = 8.dp),
                color = Color.White
            )
        }
    }
}

// Function to insert into call log
private fun insertCallLog(context: Context, phoneNumber: String, duration: String) {
    val values = ContentValues().apply {
        put(CallLog.Calls.NUMBER, phoneNumber)
        put(CallLog.Calls.DATE, System.currentTimeMillis())
        put(CallLog.Calls.DURATION, duration.toLong()) // Duration in seconds
        put(CallLog.Calls.TYPE, CallLog.Calls.INCOMING_TYPE) // or OUTGOING_TYPE or MISSED_TYPE
        put(CallLog.Calls.NEW, 1)
    }
    context.contentResolver.insert(CallLog.Calls.CONTENT_URI, values)
}

fun addContact(context: Context, phoneNumber: String, name: String) {
    val intent = Intent(ContactsContract.Intents.Insert.ACTION).apply {
        type = ContactsContract.RawContacts.CONTENT_TYPE
        putExtra(ContactsContract.Intents.Insert.NAME, name)
        putExtra(ContactsContract.Intents.Insert.PHONE, phoneNumber)
    }
    context.startActivity(intent)
}

fun isPhoneNumberInContacts(context: Context, phoneNumber: String): Boolean {
    val contentResolver = context.contentResolver
    val uri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI

    val projection = arrayOf(ContactsContract.CommonDataKinds.Phone.NUMBER)

    val selection = "${ContactsContract.CommonDataKinds.Phone.NUMBER} LIKE ?"
    val selectionArgs = arrayOf("%$phoneNumber%")

    val cursor: Cursor? = contentResolver.query(
        uri,
        projection,
        selection,
        selectionArgs,
        null
    )

    cursor.use { // Auto close
        if (it != null && it.moveToFirst()) {
            return true // Number found
        }
    }
    return false // Number not found
}