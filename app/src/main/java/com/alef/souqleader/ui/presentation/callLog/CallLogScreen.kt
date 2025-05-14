package com.alef.souqleader.ui.presentation.callLog

import android.Manifest
import android.app.Activity
import android.content.ContentProviderOperation
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.database.Cursor
import android.provider.CallLog
import android.provider.ContactsContract
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.TextFieldDefaults
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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import com.alef.souqleader.R
import com.alef.souqleader.data.remote.dto.Lead
import com.alef.souqleader.ui.MainActivity
import com.alef.souqleader.ui.MainViewModel
import com.alef.souqleader.ui.presentation.SharedViewModel
import com.alef.souqleader.ui.presentation.addlead.TextFiledItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddCallLogScreen(
    navController: NavController,
    modifier: Modifier,
    mainViewModel: MainViewModel,
    lead: Lead?
) {
    val ctx = LocalContext.current
    var caller by remember { mutableStateOf(lead?.name) }
    var calledNumber by remember { mutableStateOf(lead?.phone) }
    var duration by remember { mutableStateOf("") }
    var notes by remember { mutableStateOf("") }
    var exists by remember { mutableStateOf<Boolean>(false) }
    var isCaller by remember { mutableStateOf(false) }
    var isCalledNumber by remember { mutableStateOf(false) }
    var isDuration by remember { mutableStateOf(false) }
    mainViewModel.isCall = lead != null
    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            // Permission granted -> Insert into Call Log
            insertCallLog(ctx, calledNumber?:"", duration)
        } else {
            // Permission denied -> Show some message or handle accordingly
        }
    }


    val permissionLauncher1 = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            isPhoneNumberInContacts(ctx, calledNumber?:"")
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


        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp),
            value = caller?:"",
            placeholder = {
                Text(
                    text = stringResource(R.string.name), style = TextStyle(fontSize = 13.sp)
                )
            },

            //  keyboardActions = KeyboardActions(onNext = { focusRequester.requestFocus() }),

            textStyle = TextStyle(fontSize = 13.sp),
            colors = TextFieldDefaults.textFieldColors(
                cursorColor = colorResource(id = R.color.black),
                disabledLabelColor = colorResource(id = R.color.transparent),
                focusedIndicatorColor = colorResource(id = R.color.transparent),
                unfocusedIndicatorColor = colorResource(id = R.color.transparent),
                unfocusedLabelColor = colorResource(id = R.color.transparent)
            ),
            onValueChange = {
                caller = it
                isCaller = it.isEmpty()
            },
            shape = RoundedCornerShape(8.dp),
            singleLine = true,

            )

        if (isCaller) {
            Text(
                text = stringResource(R.string.please_enter_valid_text),
                fontSize = 12.sp,
                color = colorResource(id = R.color.red)
            )
        }



        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp),
            value = calledNumber?:"",
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Number, imeAction = ImeAction.Next
            ),
            placeholder = {
                Text(
                    text = stringResource(R.string.phone), style = TextStyle(fontSize = 13.sp)
                )
            },

            //  keyboardActions = KeyboardActions(onNext = { focusRequester.requestFocus() }),

            textStyle = TextStyle(fontSize = 13.sp),
            colors = TextFieldDefaults.textFieldColors(
                cursorColor = colorResource(id = R.color.black),
                disabledLabelColor = colorResource(id = R.color.transparent),
                focusedIndicatorColor = colorResource(id = R.color.transparent),
                unfocusedIndicatorColor = colorResource(id = R.color.transparent),
                unfocusedLabelColor = colorResource(id = R.color.transparent)
            ),
            onValueChange = {
                calledNumber = it
                isCalledNumber = it.isEmpty()
            },
            shape = RoundedCornerShape(8.dp),
            singleLine = true,

            )
        if (isCalledNumber) {
            Text(
                text = stringResource(R.string.please_enter_valid_text),
                fontSize = 12.sp,
                color = colorResource(id = R.color.red)
            )
        }

//        TextFiledItem(stringResource(R.string.duration_seconds), true) {
//            duration = it
//        }


        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp),
            value = duration,
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Number, imeAction = ImeAction.Next
            ),
            placeholder = {
                Text(
                    text = stringResource(R.string.duration), style = TextStyle(fontSize = 13.sp)
                )
            },

            //keyboardActions = KeyboardActions(onNext = { focusRequester.requestFocus() }),

            textStyle = TextStyle(fontSize = 13.sp),
            colors = TextFieldDefaults.textFieldColors(
                cursorColor = colorResource(id = R.color.black),
                disabledLabelColor = colorResource(id = R.color.transparent),
                focusedIndicatorColor = colorResource(id = R.color.transparent),
                unfocusedIndicatorColor = colorResource(id = R.color.transparent),
                unfocusedLabelColor = colorResource(id = R.color.transparent)
            ),
            onValueChange = {
                duration = it
                isDuration = it.isEmpty()
            },
            shape = RoundedCornerShape(8.dp),
            singleLine = true,

            )
        if (isDuration) {
            Text(
                text = stringResource(R.string.please_enter_valid_text),
                fontSize = 12.sp,
                color = colorResource(id = R.color.red)
            )
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
                when {
                    caller?.isEmpty() == true -> {
                        isCaller = true
                    }
                    calledNumber?.isEmpty() == true -> {
                        isCalledNumber = true
                    }
                    duration.isEmpty() -> {
                        isDuration = true
                    }
                    else -> {
                        when (PackageManager.PERMISSION_GRANTED) {
                            ContextCompat.checkSelfPermission(
                                ctx,
                                Manifest.permission.WRITE_CALL_LOG,
                            ) -> {
//                                addContact(
//                                    ctx,
//                                    calledNumber, caller
//                                )
                                (ctx as MainActivity).addContactLauncher?.let {
                                    mainViewModel.selectedLead = calledNumber?:""
                                    launchAddContact(ctx,  calledNumber?:"", caller?:"",
                                        it
                                    )
                                }

                                insertCallLog(ctx, calledNumber?:"", duration) // Add call log
                            }

                            else -> {
                                permissionLauncher.launch(Manifest.permission.WRITE_CALL_LOG)
                            }
                        }
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

fun launchAddContact(context: Context, phoneNumber: String, name: String, launcher: ActivityResultLauncher<Intent>) {
    val intent = Intent(ContactsContract.Intents.Insert.ACTION).apply {
        type = ContactsContract.RawContacts.CONTENT_TYPE
        putExtra(ContactsContract.Intents.Insert.NAME, name)
        putExtra(ContactsContract.Intents.Insert.PHONE, phoneNumber)
    }
    launcher.launch(intent)
}