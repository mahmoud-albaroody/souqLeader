package com.alef.souqleader.ui.presentation.allLeads

import android.Manifest
import android.content.ActivityNotFoundException
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.provider.CallLog
import android.widget.TextView
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.AlertDialog
import androidx.compose.material.Checkbox
import androidx.compose.material.CheckboxDefaults
import androidx.compose.material.Divider
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.Switch
import androidx.compose.material.TextButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Send
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.listSaver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.core.text.HtmlCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.alef.souqleader.R
import com.alef.souqleader.Resource
import com.alef.souqleader.WebSocketClient
import com.alef.souqleader.data.remote.dto.FilterRequest
import com.alef.souqleader.data.remote.dto.Lead
import com.alef.souqleader.domain.model.AccountData
import com.alef.souqleader.ui.MainViewModel
import com.alef.souqleader.ui.extention.toJson
import com.alef.souqleader.ui.navigation.Screen
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun AllLeadsScreen(
    navController: NavController, modifier: Modifier,
    leadId: String?,
    mainViewModel: MainViewModel
) {
    val viewModel: AllLeadViewModel = hiltViewModel()
    viewModel.updateBaseUrl(AccountData.BASE_URL)
    val ctx = LocalContext.current
    var totalElementsCount by remember { mutableIntStateOf(0) }
    val leads = remember { mutableStateListOf<Lead>() }
    val emailAddresses = remember { mutableStateListOf<Pair<String, String>>() }
    val savedSMSMessages = remember { mutableStateListOf<Pair<String, String>>() }
    var savedWhatsMessages = remember { mutableStateListOf<String>() }
    val contactList = rememberSaveable(
        saver = listSaver(
            save = { it.toList() },
            restore = { mutableStateListOf<String>().apply { addAll(it) } }
        )
    ) {
        mutableStateListOf<String>()
    }
    var messageToSend by rememberSaveable { mutableStateOf("initialMessage") }

    // Flag to track whether we've already sent a message
    var isMessageSent by rememberSaveable { mutableStateOf(false) }
    var isMail by rememberSaveable { mutableStateOf(false) }

    val sheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden,
        skipHalfExpanded = true
    )
    val coroutineScope = rememberCoroutineScope()
    // On Resume logic using LifecycleEventObserver
    val lifecycleOwner = LocalLifecycleOwner.current
    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_RESUME) {
                viewModel.prevMessages()
                viewModel.prevMails()
                if (!isMessageSent && contactList.isNotEmpty()) {

                    // Remove first contact and update the list
                    contactList.removeAt(0)
                    isMessageSent = false

                    if (contactList.isNotEmpty()) {

                        val nextContact = contactList.first()
                        // Send the next message
                        sendWhatsAppMessage(ctx, nextContact, messageToSend)
                        isMessageSent = true
                    }
                }
            }
        }
        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }
    LaunchedEffect(key1 = true) {
        viewModel.prevMessages()
        viewModel.prevMails()
        mainViewModel.showMenuContact = true
        when (leadId) {
            "100" -> {
                viewModel.delayLeads(viewModel.page)
            }

            "200" -> {
                viewModel.duplicated(viewModel.page)
            }

            else -> {
                leadId?.let {
                    viewModel.getLeadByStatus(it, viewModel.page)
                }
            }
        }


        viewModel.viewModelScope.launch {
            viewModel.stateListOfLeads.collect {
                when (it) {
                    is Resource.Success -> {
                        it.data?.info?.let {
                            it.count?.let {
                                totalElementsCount = it
                            }
                        }
                        it.data?.data?.let { it1 ->

                            if (viewModel.page == 1) {
                                leads.clear()
                                leads.addAll(it1)
                            } else {
                                leads.addAll(it1)
                            }
                        }
                        mainViewModel.showLoader = false
                    }

                    is Resource.Loading -> {
                        if (viewModel.page == 1) mainViewModel.showLoader = true
                    }

                    is Resource.DataError -> {
                        mainViewModel.showLoader = false
                    }
                }
            }
        }
        viewModel.viewModelScope.launch {
            viewModel.stateFilterLeads.collect {
                when (it) {
                    is Resource.Success -> {
                        it.data?.data?.let { it1 ->
                            leads.clear()
                            leads.addAll(it1)
                        }
                        mainViewModel.showLoader = false
                    }

                    is Resource.Loading -> {

                    }

                    is Resource.DataError -> {
                        mainViewModel.showLoader = false
                    }
                }

            }
        }
        viewModel.viewModelScope.launch {
            viewModel.getWhatsMessage.collect {
                savedWhatsMessages.clear()
                it.data?.map { msg -> msg.message }?.let { messages ->
                    savedWhatsMessages.addAll(messages)
                }
            }
        }
        viewModel.viewModelScope.launch {
            viewModel.getMailMessage.collect {
                savedSMSMessages.clear()
                it.data?.forEach {
                    savedSMSMessages.add(Pair(it.subject, it.message))
                }
            }
        }
    }

    LaunchedEffect(key1 = Unit) {
        mainViewModel.onWhatsClick.collect {
            coroutineScope.launch {
                isMail = false
                sheetState.show()
            }

        }
    }
    LaunchedEffect(key1 = Unit) {
        mainViewModel.onSmsMailClick.collect {
            contactList.clear()
            leads.filter { it.selected }.forEach {
                contactList.add(it.phone.toString())
            }

            sendSms(ctx, contactList, "")
           // viewModel.sendSms()
        }
    }
    LaunchedEffect(key1 = Unit) {
        mainViewModel.onSendMailClick.collect {
            coroutineScope.launch {
                emailAddresses.clear()
                leads.filter { it.selected }.forEach {
                    emailAddresses.add(Pair(it.name.toString(), it.email.toString()))
                }
                isMail = true
                sheetState.show()
            }
        }
    }


    DisposableEffect(Unit) {
        onDispose {
            mainViewModel.showMenuContact = false
            mainViewModel.showSendContact = false
        }
    }
    AllLeadScreen(navController,
        mainViewModel,
        setKeyword = {
            if (it.isEmpty()) {
                viewModel.page = 1
                leadId?.let { viewModel.getLeadByStatus(it, page = viewModel.page) }
            } else {
                viewModel.leadsFilter(
                    FilterRequest(
                        name = it, status = leadId
                    )
                )
            }
        },
        leads,
        leadId,
        totalElementsCount,
        sheetState,
        isMail,
        emailAddresses,
        savedWhatsMessages,
        savedSMSMessages,
        loadMore = {
            leadId?.let {
                viewModel.getLeadByStatus(it, ++viewModel.page)
            }
        },
        onCancelClick = {
            coroutineScope.launch {
                sheetState.hide()
            }
        },
        onSendClick = { title, body, isSaved, isHtml ->
            if (title.isEmpty()) {
                coroutineScope.launch {
                    contactList.clear()
                    leads.filter { it.selected }.forEach {
                        contactList.add(it.phone.toString())
                    }
                    messageToSend = body
                    if (contactList.isNotEmpty()) {
                        val firstContact = contactList.first()
                        sendWhatsAppMessage(ctx, firstContact, messageToSend)
                        viewModel.sendWhatsappMessage(messageToSend, isSaved, contactList.toList())
                        isMessageSent = false
                    }
                    sheetState.hide()
                }
            } else {
                coroutineScope.launch {
                    val emailAddresses1: ArrayList<Int> = arrayListOf()
                    emailAddresses.clear()
                    leads.filter { it.selected }.forEach {
                        emailAddresses.add(Pair(it.name.toString(), it.email.toString()))
                        it.id?.let { it1 -> emailAddresses1.add(it1) }
                    }
                    sendEmail(ctx, emailAddresses, title, body)
                    viewModel.sendMail(
                        subject = title,
                        body = body,
                        fromEmail = AccountData.email,
                        isSaved = isSaved,
                        isHtml = isHtml,
                        emailAddresses1.toList()
                    )
                    sheetState.hide()
                }
            }

        })

}


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun AllLeadScreen(
    navController: NavController,
    mainViewModel: MainViewModel,
    setKeyword: (String) -> Unit,
    leads: List<Lead>,
    leadId: String?,
    totalElementsCount: Int,
    sheetState: ModalBottomSheetState,
    isMail: Boolean,
    recipients: SnapshotStateList<Pair<String, String>>,
    savedWhatsMessages: List<String>,
    savedTemplates: List<Pair<String, String>>,
    loadMore: () -> Unit,
    onSendClick: (String, String, Boolean, Boolean) -> Unit,
    onCancelClick: () -> Unit
) {
    val selectedIdList = remember { mutableStateListOf<String>() }


    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp.dp
    val ctx = LocalContext.current
    var selectedLead by remember { mutableStateOf<Lead?>(null) }
    val messages = remember { mutableStateListOf<String>() }

    val socket = remember {
        WebSocketClient { msg ->
            messages.add(msg)
        }
    }

    AddCallDetailsDialog(
        showDialog = mainViewModel.showDialog,
        onDismiss = {
            mainViewModel.showDialog = false

        },
        onConfirm = {
            mainViewModel.showDialog = false
            val leadJson = selectedLead.toJson()
            navController.navigate(
                Screen.AddCallLogScreen.route
                    .plus("?" + Screen.AddCallLogScreen.objectName + "=${leadJson}")
            )

        }
    )
    LaunchedEffect(Unit) {
        socket.connect()
    }
    DisposableEffect(Unit) {
        onDispose {
            socket.close()
        }
    }


    ModalBottomSheetLayout(
        sheetState = sheetState,
        sheetContent = {
            if (isMail) {
                ComposeEmailPage(recipients, savedTemplates, onCancel = {
                    onCancelClick()
                }, onSend = { title, body, isSaved, isHtml ->
                    onSendClick(title, body, isSaved, isHtml)
                })
            } else {
                NewMessageSheetContent(
                    onCancel = {
                        onCancelClick()
                    },
                    onSend = { message, isSave ->
                        onSendClick("", message, isSave, false)
                    },
                    savedWhatsMessages
                )
            }
        }
    ) {
        Box(
            Modifier
                .background(colorResource(id = R.color.white))
                .padding(horizontal = 24.dp, vertical = 16.dp)
                .fillMaxSize()
        ) {
            Column(Modifier.height(screenHeight)) {
                Search(stringResource(R.string.search), setKeyword = {
                    setKeyword(it)
                }, onFilterClick = {
                    navController.navigate(Screen.FilterScreen.route.plus("/${leadId}")) {
                        launchSingleTop = true
                    }
                })

                LazyColumn(
                    Modifier.fillMaxWidth()
                ) {
                    items(leads) { leadItem ->
                        AllLeadsItem(leadItem, onItemClick = { lead ->
                            navController.navigate(
                                Screen.LeadDetailsScreen.route.plus("/${lead.id.toString()}")
                            )

                        }, onLongPress = { lead ->
                            if (selectedIdList.find { it.toInt() == lead.id } == null) {
                                selectedIdList.add(lead.id.toString())
                                //    selectedPhoneList.add(lead.phone.toString())
                            } else {
                                selectedIdList.remove(lead.id.toString())
                                //selectedPhoneList.remove(lead.phone.toString())
                            }
                        }, onCallClick = { lead ->
                            socket.sendData(
                                leadItem.id.toString(),
                                "call",
                                lead.sales_id.toString()
                            )
                            selectedLead = lead
                            val u = Uri.parse(
                                "tel:" + selectedLead?.phone.toString()
                            )

                            // Create the intent and set the data for the
                            // intent as the phone number.
                            val i = Intent(Intent.ACTION_DIAL, u)
                            try {
                                // Launch the Phone app's dialer with a phone
                                // number to dial a call.
                                ctx.startActivity(i)
                                mainViewModel.showDialog = true
                            } catch (s: SecurityException) {

                                // show() method display the toast with
                                // exception message.
                                Toast
                                    .makeText(ctx, "An error occurred", Toast.LENGTH_LONG)
                                    .show()
                            }

                        },
                            onMailClick = { lead ->
//                                contactList.add("+201012953520")
//                                contactList.add("+201010079486")
                                // sendSms(ctx, contactList, messageToSend)
                                //     sendEmail(ctx, emailAddresses, "subject", "message")

//                                if (contactList.isNotEmpty()) {
//                                    val firstContact = contactList.first()
//                                    sendWhatsAppMessage(ctx, firstContact, messageToSend)
//                                    isMessageSent = false
//                                }

                                socket.sendData(
                                    lead.id.toString(),
                                    "email",
                                    lead.sales_id.toString()
                                )
                            },
                            onSmsClick = { lead ->
                                socket.sendData(lead.id.toString(), "sms", lead.sales_id.toString())
                            },
                            onWhatsClick = { lead ->
                                socket.sendData(
                                    lead.id.toString(),
                                    "whatsapp",
                                    lead.sales_id.toString()
                                )
                            }
                        )
                    }
                    if (totalElementsCount > leads.size && leads.isNotEmpty()) item {
                        loadMore()
                        Row(
                            Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center
                        ) {
                            CircularProgressIndicator(
                                modifier = Modifier.width(16.dp),
                                color = MaterialTheme.colorScheme.secondary,
                                trackColor = MaterialTheme.colorScheme.surfaceVariant,
                            )
                        }
                    }
                }

            }
            if (selectedIdList.isNotEmpty()) Button(modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter),
                shape = RoundedCornerShape(15.dp),
                colors = ButtonDefaults.buttonColors(colorResource(id = R.color.blue2)),
                onClick = {
                    val joinedString = selectedIdList.joinToString(separator = ",")
                    navController.navigate(Screen.LeadUpdateScreen.route.plus("/${joinedString}"))
                }) {
                Text(
                    text = stringResource(R.string.add_action),
                    modifier = Modifier.padding(vertical = 8.dp)
                )
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun AllLeadsItem(
    lead: Lead, onItemClick: (Lead) -> Unit,
    onLongPress: (Lead) -> Unit,
    onCallClick: (Lead) -> Unit,
    onMailClick: (Lead) -> Unit,
    onSmsClick: (Lead) -> Unit,
    onWhatsClick: (Lead) -> Unit,
) {
    var selected by remember { mutableStateOf(false) }
    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp.dp
    val ctx = LocalContext.current
    selected = lead.selected

    Card(
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier
            .fillMaxWidth()
//            .height(screenHeight / 3f)
            .padding(6.dp)
            .combinedClickable(onLongClick = {
                lead.selected = !lead.selected
                selected = lead.selected
                onLongPress(lead)
            }, onDoubleClick = { /*....*/ }, onClick = { onItemClick(lead) }),
//            .clickable {
////                lead.selected = !lead.selected
////                selected = lead.selected
//                onItemClick(lead)
//            },
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Box(Modifier.fillMaxSize()) {
//            if (selected)
//                Image(
//                    painterResource(R.drawable.select_box),
//                    contentDescription = "",
//                    Modifier.align(Alignment.TopEnd)
//                )
            Checkbox(
                modifier = Modifier.align(Alignment.TopEnd),
                checked = selected,
                onCheckedChange = {
                    lead.selected = !lead.selected
                    selected = lead.selected
                    onLongPress(lead)
                    selected = it
                },
                colors = CheckboxDefaults.colors(
                    checkedColor = colorResource(id = R.color.blue),
                    uncheckedColor = colorResource(id = R.color.blue),
                    checkmarkColor = Color.White
                )
            )

            Column(
                Modifier
                    .padding(horizontal = 16.dp, vertical = 8.dp)
                    .fillMaxSize(),
                verticalArrangement = Arrangement.SpaceBetween

            ) {

                Row(
                    Modifier
                        .fillMaxWidth()
                        .padding(
                            vertical = 8.dp
                        )
                        .padding(end = 50.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                ) {
                    Image(
                        painterResource(R.drawable.client_placeholder),
                        contentDescription = "",
                        modifier = Modifier.padding(end = 8.dp)
                    )

                    Column(
                        Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 4.dp)
                            .padding(top = 6.dp)
                    ) {
                        Text(
                            text = lead.name ?: "", style = TextStyle(
                                fontSize = 16.sp, color = colorResource(id = R.color.blue)
                            )
                        )
                        if (!lead.phone.isNullOrEmpty() && lead.phone.length > 4) {
                            Text(
                                text = (lead.phone.substring(
                                    0,
                                    3
                                ) + "*".repeat(lead.phone.length - 3)), style = TextStyle(
                                    fontSize = 14.sp, fontWeight = FontWeight.SemiBold
                                )
                            )
                        } else {
                            Text(
                                text = (lead.phone.toString()), style = TextStyle(
                                    fontSize = 14.sp, fontWeight = FontWeight.SemiBold
                                )
                            )
                        }

                    }

                }

                Row(
                    Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    verticalAlignment = Alignment.Bottom,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Image(
                            painterResource(R.drawable.sales_name_icon),
                            contentDescription = "",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.size(20.dp)
                        )
                        Text(
                            text = lead.sales_name ?: "", style = TextStyle(
                                fontSize = 14.sp,
                            ), modifier = Modifier.padding(start = 4.dp)
                        )
                    }
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    lead.project_name.let {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.weight(1f)
                        ) {
                            Image(
                                painterResource(R.drawable.project_icon),
                                contentDescription = "",
                                contentScale = ContentScale.Crop,
                                modifier = Modifier.size(20.dp)
                            )
                            Text(
                                text = it ?: "", style = TextStyle(
                                    fontSize = 14.sp,
                                ), modifier = Modifier.padding(start = 4.dp)
                            )
                        }
                    }
                    lead.budget?.let {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.weight(1f)
                        ) {
                            Image(
                                painterResource(R.drawable.coin),
                                contentDescription = "",
                                contentScale = ContentScale.Crop,
                                modifier = Modifier.size(20.dp)
                            )

                            Text(
                                text = it, style = TextStyle(
                                    fontSize = 14.sp,
                                ), modifier = Modifier.padding(start = 4.dp)
                            )
                        }
                    }
                }


                Row(
                    modifier = Modifier.padding(vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painterResource(R.drawable.notes_icon),
                        contentDescription = "",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.size(20.dp)
                    )
                    lead.note?.let {
                        Text(
                            text = it, style = TextStyle(
                                fontSize = 11.sp, color = colorResource(id = R.color.gray)
                            ), modifier = Modifier.padding(start = 4.dp)
                        )
                    }
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    Image(painterResource(R.drawable.call_icon),
                        contentDescription = "",
                        Modifier
                            .size(60.dp, 40.dp)
                            .clickable {
                                onCallClick(lead)
                            })
                    Image(painterResource(R.drawable.sms_icon),
                        contentDescription = "",
                        Modifier
                            .size(60.dp, 40.dp)
                            .clickable {

                                openSMSApp(
                                    phoneNumber = lead.phone.toString(),
                                    message = ctx.getString(R.string.enter_your_message),
                                    context = ctx
                                )
                                onSmsClick(lead)
                            })

                    Image(painterResource(R.drawable.mail_icon),
                        contentDescription = "",
                        Modifier
                            .size(60.dp, 40.dp)
                            .clickable {
                                ctx.sendMail(
                                    to = lead.email ?: "", subject = ""
                                )
                                onMailClick(lead)
                            })
                    Image(painterResource(R.drawable.whats_icon),
                        contentDescription = "",
                        Modifier
                            .size(60.dp, 40.dp)
                            .clickable {
                                onWhatsClick(lead)
                                ctx.startActivity(
                                    // on below line we are opening the intent.
                                    Intent(
                                        // on below line we are calling
                                        // uri to parse the data
                                        Intent.ACTION_VIEW, Uri.parse(
                                            // on below line we are passing uri,
                                            // message and whats app phone number.
                                            java.lang.String.format(
                                                "https://api.whatsapp.com/send?phone=%s&text=%s",
                                                lead.phone,
                                                ""
                                            )
                                        )
                                    )
                                )
                            })
                }
            }
        }
    }
}

@Composable
fun AddToCallLogButton(phoneNumber: String) {
    val context = LocalContext.current

    Button(onClick = {
        if (ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.WRITE_CALL_LOG
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            val values = ContentValues().apply {
                put(CallLog.Calls.NUMBER, phoneNumber)
                put(CallLog.Calls.DATE, System.currentTimeMillis())
                put(CallLog.Calls.DURATION, 0) // Duration in seconds
                put(
                    CallLog.Calls.TYPE,
                    CallLog.Calls.INCOMING_TYPE
                ) // or OUTGOING_TYPE or MISSED_TYPE
                put(CallLog.Calls.NEW, 1)
                put(CallLog.Calls.CACHED_NAME, "") // if you have a name
                put(CallLog.Calls.CACHED_NUMBER_TYPE, 0)
                put(CallLog.Calls.CACHED_NUMBER_LABEL, "")
            }
            context.contentResolver.insert(CallLog.Calls.CONTENT_URI, values)
        } else {
            // Handle the case when permission is NOT granted
            // You should ask for permission here
        }
    }) {
        Text(text = "Add to Call Log")
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Search(text: String, setKeyword: (String) -> Unit, onFilterClick: () -> Unit) {
    var keyword by remember { mutableStateOf("") }
    Card(
        Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        shape = RoundedCornerShape(8.dp),
    ) {
        Row(
            Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Image(
                painterResource(R.drawable.search_icon),
                modifier = Modifier
                    .size(30.dp)
                    .weight(0.5f),
                contentDescription = "",
            )
            TextField(
                modifier = Modifier.weight(3f),
                value = keyword,
                placeholder = {
                    Text(text = text)
                },
                colors = TextFieldDefaults.textFieldColors(
                    cursorColor = colorResource(id = R.color.black),
                    disabledLabelColor = colorResource(id = R.color.blue),
                    focusedIndicatorColor = colorResource(id = R.color.transparent),
                    unfocusedIndicatorColor = colorResource(id = R.color.transparent)
                ),
                onValueChange = {
                    keyword = it
                    setKeyword(keyword)
                },
//                shape = RoundedCornerShape(8.dp),
                singleLine = true,
            )
            Image(painterResource(R.drawable.filter_icon),
                contentDescription = "",
                Modifier
                    .size(30.dp)
                    .weight(0.5f)
                    .clickable {
                        onFilterClick.invoke()
                    })
        }
    }

}

fun Context.sendMail(to: String, subject: String) {
    try {
        val intent = Intent(Intent.ACTION_SEND)
        intent.type = "vnd.android.cursor.item/email" // or "message/rfc822"
        intent.putExtra(Intent.EXTRA_EMAIL, arrayOf(to))
        intent.putExtra(Intent.EXTRA_SUBJECT, subject)
        startActivity(intent)
    } catch (e: ActivityNotFoundException) {
        // TODO: Handle case where no email app is available
    } catch (t: Throwable) {
        // TODO: Handle potential other type of exceptions
    }
}

fun openSMSApp(phoneNumber: String, message: String, context: Context) {
    val uri = Uri.parse("smsto:$phoneNumber")
    val intent = Intent(Intent.ACTION_SENDTO, uri)
    intent.putExtra("sms_body", message)
    try {
        context.startActivity(intent)
    } catch (e: Exception) {
        e.printStackTrace()
        // Handle the error if SMS app is not available
    }
}

fun sendWhatsAppMessage(context: Context, phoneNumber: String, message: String) {
    try {
//        context.startActivity(
//            // on below line we are opening the intent.
//            Intent(
//                // on below line we are calling
//                // uri to parse the data
//                Intent.ACTION_VIEW, Uri.parse(
//                    // on below line we are passing uri,
//                    // message and whats app phone number.
//                    java.lang.String.format(
//                        "https://api.whatsapp.com/send?phone=%s&text=%s",
//                        phoneNumber,
//                        "message"
//                    )
//                )
//            )
//        )
        val uri = Uri.parse("https://wa.me/$phoneNumber?text=${Uri.encode(message)}")
        val intent = Intent(Intent.ACTION_VIEW, uri)
        intent.setPackage("com.whatsapp")  // Directly open WhatsApp
        context.startActivity(intent)
    } catch (e: Exception) {
        e.printStackTrace()
        // Show error message or handle gracefully
    }
}

@Composable
fun AddCallDetailsDialog(
    showDialog: Boolean,
    onDismiss: () -> Unit,
    onConfirm: () -> Unit
) {
    if (showDialog) {
        AlertDialog(
            onDismissRequest = onDismiss,
            title = {
                Text(text = stringResource(R.string.add_lead_add_call_details))
            },
            text = {
                Text(text = stringResource(R.string.would_you_like_to_add_or_log_the_details_of_the_call))
            },
            confirmButton = {
                TextButton(onClick = onConfirm) {
                    Text(text = stringResource(R.string.yes))
                }
            },
            dismissButton = {
                TextButton(onClick = onDismiss) {
                    Text(text = stringResource(R.string.no))
                }
            }
        )
    }
}

@Composable
fun NewMessageSheetContent(
    onCancel: () -> Unit,
    onSend: (String, Boolean) -> Unit,
    savedMessages: List<String>
) {
    var message by rememberSaveable { mutableStateOf("") }
    var saveMessage by rememberSaveable { mutableStateOf(false) }



    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.90f)
            .background(Color(0xFFEFEFEF)) // Light background
            .padding(16.dp)
    ) {
        // Header
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            TextButton(onClick = onCancel) {
                Text(stringResource(id = R.string.cancel), color = Color.Red)
            }
            Spacer(modifier = Modifier.weight(1f))
            Text(stringResource(R.string.new_message), style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.weight(1f))
            IconButton(onClick = { onSend(message, saveMessage) }) {
                Icon(
                    imageVector = Icons.Default.Send,
                    contentDescription = stringResource(R.string.send),
                    tint = Color.Gray
                )
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        // "Saved Messages" label
        Text(
            text = stringResource(R.string.saved_messages),
            style = MaterialTheme.typography.labelSmall,
            color = Color.Gray
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Saved Messages
        LazyRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(savedMessages) { saved ->
                Card(
                    shape = RoundedCornerShape(12.dp),
                    border = BorderStroke(1.dp, Color(0xFF72FC7F)),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFFDFF5E1)), // light green
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .clickable { message = saved }

                ) {
                    Text(
                        text = saved,
                        modifier = Modifier.padding(8.dp),
                        fontSize = 11.sp
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        // New Message Input
        OutlinedTextField(
            value = message,
            onValueChange = { message = it },
            placeholder = { Text(stringResource(id = R.string.new_message)) },
            modifier = Modifier
                .fillMaxWidth()
                .height(160.dp),
            maxLines = 6,
            shape = RoundedCornerShape(16.dp)
        )

        Spacer(modifier = Modifier.height(4.dp))

        // Character count (bottom right corner)
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End
        ) {
            Text("${message.length}/1000", fontSize = 12.sp, color = Color.Gray)
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Save toggle
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Icon(
                imageVector = Icons.Default.BookmarkBorder,
                contentDescription = null,
                tint = Color(0xFF4CAF50)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(stringResource(R.string.save_this_message_for_future_use))
            Spacer(modifier = Modifier.weight(1f))
            Switch(
                checked = saveMessage,
                onCheckedChange = { saveMessage = it }
            )
        }
    }
}

fun sendSms(context: Context, phoneNumbers: List<String>, message: String) {
    val recipients = phoneNumbers.joinToString(",")
    val uri = Uri.parse("smsto:$recipients")
    val intent = Intent(Intent.ACTION_SENDTO, uri).apply {
        putExtra("sms_body", message)
    }
    context.startActivity(intent)
}

fun sendEmail(
    context: Context,
    emailAddresses: SnapshotStateList<Pair<String, String>>,
    subject: String,
    message: String
) {
    val emailAddresses1: ArrayList<String> = arrayListOf()
    emailAddresses.forEach {
        emailAddresses1.add(it.second)
    }
    val intent = Intent(Intent.ACTION_SENDTO).apply {
        data = android.net.Uri.parse("mailto:")
        putExtra(Intent.EXTRA_EMAIL, emailAddresses1.toTypedArray())
        putExtra(Intent.EXTRA_SUBJECT, subject)
        putExtra(Intent.EXTRA_TEXT, message)
    }
    context.startActivity(Intent.createChooser(intent, "Send Email"))
}


@Composable
fun ComposeEmailPage(
    recipients: SnapshotStateList<Pair<String, String>>, savedTemplates: List<Pair<String, String>>,
    onSend: (String, String, Boolean, Boolean) -> Unit,
    onCancel: () -> Unit
) {

    var subject by remember { mutableStateOf("") }
    var body by remember { mutableStateOf("") }
    var useHtml by remember { mutableStateOf(false) }
    var saveMessage by remember { mutableStateOf(false) }
    var showPreview by remember { mutableStateOf(false) }
//    val savedTemplates = listOf(
//        Triple("new Offers", "new Offers", "Offer until end of spring..."),
//        Triple("Hi", "Dear Mylead, Masry,", "I hope this message finds you..."),
//        Triple("Hi", "<h1>Dear Mylead, Masry,", "I hope this message finds..."),
//        Triple("Hello", "Dear Mylead, neqlead, Rana,", "I hope this message f..."),
//        Triple("Hello", "Dear Mylead, neqlead, Rana,", "I hope this message f...")
//    )

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.90f)
            .background(Color(0xFFFFFFFF)) // Light background
            .verticalScroll(rememberScrollState())
    ) {
        // Top Bar
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFF007AFF))
        ) {
            Text(text = stringResource(id = R.string.cancel),
                color = Color.White, modifier = Modifier
                    .padding(vertical = 16.dp, horizontal = 16.dp)
                    .clickable {
                        onCancel()
                    })
            Text(
                modifier = Modifier
                    .padding(vertical = 16.dp, horizontal = 16.dp),
                text = stringResource(R.string.compose_email),
                color = Color.White,
                fontWeight = FontWeight.Bold
            )
            Icon(
                Icons.Default.Send, contentDescription = "Send", tint = Color.White,
                modifier = Modifier
                    .padding(vertical = 16.dp, horizontal = 16.dp)
                    .clickable {
                        onSend(subject, body, saveMessage, useHtml)
                    }
            )
        }

        // RECIPIENTS SECTION
        Text(
            text = stringResource(R.string.recipients),
            modifier = Modifier.padding(16.dp),
            fontWeight = FontWeight.Bold
        )
        Column(modifier = Modifier.padding(horizontal = 16.dp)) {

            recipients.forEachIndexed { index, (name, email) ->
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                ) {
                    Icon(Icons.Default.AccountCircle, contentDescription = null, tint = Color.Green)
                    Spacer(Modifier.width(8.dp))
                    Column(modifier = Modifier.weight(1f)) {
                        Text(name, fontWeight = FontWeight.Bold)
                        Text(email)
                    }
                    IconButton(onClick = { recipients.removeAt(index) }) {
                        Icon(Icons.Default.Delete, contentDescription = "Delete", tint = Color.Red)
                    }
                }
                Divider()
            }

            // Add Recipient
//        Text(
//            text = "+ Add Recipient",
//            color = Color.Blue,
//            modifier = Modifier
//                .padding(vertical = 8.dp)
//                .clickable { recipients.add("New Recipient" to "example@email.com") }
//        )
        }

        // EMAIL CONTENT SECTION
        Text(
            stringResource(R.string.email_content),
            modifier = Modifier.padding(16.dp),
            fontWeight = FontWeight.Bold
        )
        Column(modifier = Modifier.padding(horizontal = 16.dp)) {
            OutlinedTextField(
                value = subject,
                onValueChange = { subject = it },
                label = { Text(stringResource(R.string.subject)) })

            Spacer(Modifier.height(8.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = stringResource(R.string.use_html_formatting),
                    modifier = Modifier.weight(1f)
                )
                Switch(checked = useHtml,

                    onCheckedChange = {
                        useHtml = it
                        showPreview = false

                        body = if (useHtml) {
                            if (body.contains("<h1>")) {
                                body
                            } else {
                                // Convert to HTML-formatted string (basic, safe conversion)
                                "<h1>" + body.replace("\n", "<br>") + "</h1>"
                            }

                        } else {
                            // Strip simple HTML tags
                            HtmlCompat.fromHtml(body, HtmlCompat.FROM_HTML_MODE_LEGACY).toString()
                        }

                    })
                if (useHtml) {
                    Button(onClick = { showPreview = !showPreview }) {
                        Text(
                            if (showPreview) stringResource(R.string.hide_preview) else stringResource(
                                R.string.preview_html
                            )
                        )
                    }
                }
            }

            if (showPreview) {
                AndroidView(
                    factory = { context ->
                        TextView(context).apply {
                            text = HtmlCompat.fromHtml(body, HtmlCompat.FROM_HTML_MODE_LEGACY)
                        }
                    },
                    update = {
                        it.text = HtmlCompat.fromHtml(body, HtmlCompat.FROM_HTML_MODE_LEGACY)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(150.dp)
                        .padding(top = 8.dp)
                )
            } else {
                OutlinedTextField(
                    value = body,
                    onValueChange = { body = it },
                    label = { Text(stringResource(R.string.email_body)) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(150.dp),
                    maxLines = 10
                )
            }
        }

        // SAVED TEMPLATES
        Text(
            stringResource(R.string.saved_templates),
            modifier = Modifier.padding(16.dp),
            fontWeight = FontWeight.Bold
        )
        savedTemplates.forEach { (title, preview) ->
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        subject = title
                        body =
                            if (useHtml) "<h1>$preview</h1>" else "$preview"
                    }
                    .padding(horizontal = 16.dp, vertical = 8.dp)
            ) {
                Text(text = title, color = Color.Blue, fontWeight = FontWeight.Bold)
                Text(preview, color = Color.Gray, fontSize = 12.sp)
            }
            Divider()
        }

        // SAVE MESSAGE SWITCH
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                stringResource(id = R.string.save_this_message_for_future_use),
                modifier = Modifier.weight(1f)
            )
            Switch(checked = saveMessage, onCheckedChange = { saveMessage = it })
        }

        Spacer(Modifier.height(16.dp))
    }
}
