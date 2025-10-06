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
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
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
import androidx.compose.runtime.LaunchedEffect
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
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.alef.souqleader.R
import com.alef.souqleader.Resource
import com.alef.souqleader.data.remote.dto.AllLeadStatus
import com.alef.souqleader.data.remote.dto.Lead
import com.alef.souqleader.domain.model.AccountData
import com.alef.souqleader.ui.MainActivity
import com.alef.souqleader.ui.MainViewModel
import com.alef.souqleader.ui.presentation.SharedViewModel
import com.alef.souqleader.ui.presentation.addlead.TextFiledItem
import com.alef.souqleader.ui.presentation.addlead.TextFiledItem1
import com.alef.souqleader.ui.presentation.leadUpdate.LeadUpdateViewModel
import com.alef.souqleader.ui.presentation.mainScreen.MainScreen
import com.alef.souqleader.ui.theme.AndroidCookiesTheme
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddCallLogScreen(
    navController: NavHostController,
    modifier: Modifier,
    mainViewModel: MainViewModel,
    sharedViewModel: SharedViewModel,
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
    val callLogViewModel: CallLogViewModel = hiltViewModel()
    val context = LocalContext.current
    mainViewModel.isCall = lead != null
    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            // Permission granted -> Insert into Call Log
            //  insertCallLog(ctx, calledNumber?:"", duration)
        } else {
            // Permission denied -> Show some message or handle accordingly
        }
    }

    LaunchedEffect(key1 = true) {
        callLogViewModel.viewModelScope.launch {
                callLogViewModel.quickCreate.collect {
                    Toast.makeText(
                        context, it.message.toString(),
                        Toast.LENGTH_LONG
                    ).show()
                    when (PackageManager.PERMISSION_GRANTED) {
                        ContextCompat.checkSelfPermission(
                            ctx,
                            Manifest.permission.WRITE_CALL_LOG,
                        ) -> {
                        }

                        else -> {
                            permissionLauncher.launch(Manifest.permission.WRITE_CALL_LOG)
                        }
                    }
                    navController.popBackStack()
                }
        }
    }
    val permissionLauncher1 = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            isPhoneNumberInContacts(ctx, calledNumber ?: "")
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
            value = caller ?: "",
            placeholder = {
                Text(
                    text = stringResource(R.string.name), style = TextStyle(fontSize = 13.sp)
                )
            },

            //  keyboardActions = KeyboardActions(onNext = { focusRequester.requestFocus() }),

            textStyle = TextStyle(fontSize = 13.sp),
            colors = TextFieldDefaults.colors(
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
            value = calledNumber ?: "",
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
            colors = TextFieldDefaults.colors(
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
            colors = TextFieldDefaults.colors(
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
                text = stringResource(R.string.please_enter_a_duration),
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
                        callLogViewModel.quickCreate(
                            caller ?: "",
                            calledNumber, duration, notes, lead?.id.toString()
                        )

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

fun launchAddContact(
    context: Context,
    phoneNumber: String,
    name: String,
    launcher: ActivityResultLauncher<Intent>
) {
    val intent = Intent(ContactsContract.Intents.Insert.ACTION).apply {
        type = ContactsContract.RawContacts.CONTENT_TYPE
        putExtra(ContactsContract.Intents.Insert.NAME, name)
        putExtra(ContactsContract.Intents.Insert.PHONE, phoneNumber)
    }
    launcher.launch(intent)
}