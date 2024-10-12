package com.alef.souqleader.ui.presentation.dashboardScreen

import android.util.Log
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.alef.souqleader.R
import com.alef.souqleader.Resource
import com.alef.souqleader.data.remote.dto.AllLeadStatus
import com.alef.souqleader.domain.model.AccountData
import com.alef.souqleader.ui.MainActivity
import com.alef.souqleader.ui.MainViewModel
import com.alef.souqleader.ui.navigation.Navigation1
import com.alef.souqleader.ui.navigation.Screen
import com.alef.souqleader.ui.presentation.SharedViewModel
import com.alef.souqleader.ui.presentation.login.SampleNameProvider
import com.alef.souqleader.ui.presentation.mainScreen.MainScreen
import com.alef.souqleader.ui.theme.*
import kotlinx.coroutines.launch

@Composable
fun DashboardScreen(
    navController: NavHostController,
    sharedViewModel: SharedViewModel,
    mainViewModel: MainViewModel
) {
    val viewModel: DashboardViewModel = hiltViewModel()
    viewModel.updateBaseUrl(AccountData.BASE_URL)
    val allLead = remember { mutableStateListOf<AllLeadStatus>() }
    Log.e("ddddd", AccountData.auth_token.toString())
    val context = LocalContext.current
    LaunchedEffect(key1 = true) {
        sharedViewModel.updateSalesNameState(AccountData.role_name)
        sharedViewModel.updatePhotoState(AccountData.photo)
        sharedViewModel.updateNameState(AccountData.name)
        viewModel.getLeads()
        viewModel.viewModelScope.launch {
            viewModel.allLead.collect {

                when (it) {
                    is Resource.Success -> {

                        allLead.clear()
                        it.data?.data?.let { it1 -> allLead.addAll(it1) }
                        mainViewModel.showLoader = false
                    }


                    is Resource.Loading -> {
                        mainViewModel.showLoader = true
                    }

                    is Resource.DataError -> {
                        if (it.errorCode == 401) {
                            AccountData.clear()
                            (context as MainActivity).setContent {
                                AndroidCookiesTheme {
                                    MainScreen(Modifier, navController, sharedViewModel, mainViewModel)
                                }
                            }
                        }
                        mainViewModel.showLoader = false
                    }
                }
            }
        }
    }

    LazyVerticalGrid(
        GridCells.Fixed(2),
        Modifier
            .fillMaxSize()
            .padding(vertical = 16.dp, horizontal = 24.dp)
    ) {
        items(allLead) {
            MyCardItem(it) {
                Screen.AllLeadsScreen.title = it.getTitle()
                navController.navigate(
                    Screen.AllLeadsScreen.route.plus("/${it.id}")
                )
            }
        }
    }
}

class SampleNameProvider(override val values: Sequence<Unit>) :
    PreviewParameterProvider<Unit> {

}

//@Preview
@Composable
fun MyCardItem(
    leadStatus: AllLeadStatus,
    @PreviewParameter(SampleNameProvider::class) onClick: () -> Unit
) {
    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp.dp
    val background =
        if (leadStatus.leads_count == "0") {
            Brush.verticalGradient(
                colors = listOf(
                    colorResource(id = R.color.grey1),
                    colorResource(id = R.color.grey2)
                ),
                startY = 0f,
                endY = 450f
            )
        } else {
            Brush.verticalGradient(
                colors = listOf(
                    colorResource(id = R.color.blue1),
                    colorResource(id = R.color.blue2)
                ),
                startY = 0f,
                endY = 450f
            )
        }
    Card(
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier
            .fillMaxWidth()
            .height(screenHeight / 4.6f)
            .padding(6.dp)
            .clickable(onClick = onClick),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(
            Modifier
                .fillMaxHeight()
                .background(
                    brush = background
                ),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(vertical = 2.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column(Modifier.padding(16.dp)) {
                    Text(
                        text = leadStatus.leads_count.toString(),
                        Modifier.padding(bottom = 8.dp),
                        style = TextStyle(
                            fontSize = 24.sp,
                            color = colorResource(id = R.color.white),
                            fontWeight = FontWeight.Bold
                        )
                    )
                    Text(
                        text = leadStatus.getTitle(),
                        style = TextStyle(color = colorResource(id = R.color.white))
                    )
                }
                Column(Modifier.padding(16.dp)) {
                    Image(
                        painter = rememberAsyncImagePainter(
                            AccountData.BASE_URL + leadStatus.icon
                        ),
                        contentDescription = "",
                        Modifier.size(20.dp)
                    )
                }
            }
            val trackColor = if (leadStatus.leads_count == "0") {
                colorResource(id = R.color.grey1)
            } else {
                colorResource(id = R.color.blue1)
            }
            Row {
                LinearProgressIndicator(
                    progress = { leadStatus.getPer() },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            horizontal = 16.dp, vertical = 12.dp
                        )
                        .padding(bottom = 11.dp),
                    color = colorResource(id = R.color.white),
                    trackColor = trackColor

                )
            }
        }
    }

}
