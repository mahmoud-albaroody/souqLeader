package com.alef.souqleader.ui.presentation.jobApplicationDetails

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.StarHalf
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.alef.souqleader.R
import com.alef.souqleader.data.remote.dto.Education
import com.alef.souqleader.data.remote.dto.Jobapps
import com.alef.souqleader.data.remote.dto.Language
import com.alef.souqleader.data.remote.dto.Skill
import com.alef.souqleader.data.remote.dto.WorkExperience
import android.app.DownloadManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Environment
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.ui.platform.LocalContext
import com.alef.souqleader.ui.constants.Constants.BASE_URL
import com.alef.souqleader.ui.presentation.allLeads.sendMail

@Composable
fun JobApplicationDetailsScreen(
    navController: NavController,
    jobApps: Jobapps
) {
    JobApplication(jobApps)
}

@Composable
fun JobApplication(jobApps: Jobapps) {
    val scrollState = rememberScrollState()
    Column(
        modifier = Modifier
            .verticalScroll(scrollState)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        PersonDetails(jobApps)
        if (jobApps.work_experience.isNotEmpty()) {
            PersonExperience(jobApps.work_experience)
        } else {
            EmptyText(stringResource(R.string.no_work_experience_available))
        }
        if (jobApps.skills.isNotEmpty()) {
            PersonSkills(jobApps.skills)
        } else {
            EmptyText(stringResource(R.string.no_work_skills_available))
        }
        if (jobApps.skills.isNotEmpty()) {
            PersonRating(jobApps.language)
        } else {
            EmptyText(stringResource(R.string.no_work_languages_available))
        }
        PersonCV(jobApps)
    }
}


@Composable
fun PersonDetails(jobApps: Jobapps) {
    val ctx = LocalContext.current

    Card(
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier
            .fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = colorResource(id = R.color.white)),
        elevation = CardDefaults.cardElevation(6.dp)
    ) {
        Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {

                Column(Modifier) {
                    Text(
                        text = jobApps.name,
                        style = TextStyle(
                            fontSize = 20.sp,
                            color = colorResource(id = R.color.grey2),
                            fontWeight = FontWeight.Bold
                        ),
                    )

                    if (jobApps.work_experience.isNotEmpty())
                        Text(
                            text = jobApps.work_experience[0].job_title,
                            style = TextStyle(
                                fontSize = 16.sp, color = colorResource(id = R.color.gray)
                            )
                        )
                    Column(
                        modifier =
                        Modifier
                            .fillMaxWidth()
                            .padding(top = 8.dp)

                    ) {
                        repeat(jobApps.education.size) { innerIndex ->
                            Education(jobApps.education[innerIndex])
                        }
                    }
                }

                Image(
//                    painter = rememberAsyncImagePainter(
//                        if (AccountData.photo.isNotEmpty()) {
//                            if (AccountData.photo.isNotEmpty()) {
//                                AccountData.BASE_URL + AccountData.photo
//                            } else {
//                                R.drawable.user_profile_placehoder
//                            }
//                        } else {
//
//                        }
//                    ),
                    painter = painterResource(id = R.drawable.ic_lock_open),
                    contentDescription = "",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(60.dp)
                        .clip(CircleShape)
                )
            }

            if (jobApps.phone.isNotEmpty())
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Image(
                            modifier = Modifier.size(20.dp),
                            painter = painterResource(id = R.drawable.baseline_call_24),
                            contentDescription = ""
                        )

                        Text(
                            modifier = Modifier
                                .padding(start = 8.dp),
                            fontSize = 12.sp,
                            text = jobApps.phone,
                        )
                    }
                    Image(
                        modifier = Modifier
                            .size(30.dp)
                            .clickable {
                                val u = Uri.parse(
                                    "tel:"
                                            + jobApps.phone.toString()
                                )

                                // Create the intent and set the data for the
                                // intent as the phone number.
                                val i = Intent(Intent.ACTION_DIAL, u)
                                try {

                                    // Launch the Phone app's dialer with a phone
                                    // number to dial a call.
                                    ctx.startActivity(i)
                                } catch (s: SecurityException) {

                                    // show() method display the toast with
                                    // exception message.
                                    Toast
                                        .makeText(ctx, "An error occurred", Toast.LENGTH_LONG)
                                        .show()
                                }
                            },
                        painter = painterResource(id = R.drawable.call_icon),
                        contentDescription = ""
                    )

                }
            if (jobApps.email.isNotEmpty())
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Image(
                            modifier = Modifier.size(20.dp),
                            painter = painterResource(id = R.drawable.ic_email),
                            contentDescription = ""
                        )
                        Text(
                            modifier = Modifier
                                .padding(start = 8.dp),
                            fontSize = 12.sp,
                            text = jobApps.email,
                        )
                    }
                    Image(
                        modifier = Modifier
                            .size(30.dp)
                            .clickable {
                                ctx.sendMail(
                                    to = jobApps.email,
                                    subject = ""
                                )
                            },
                        painter = painterResource(id = R.drawable.mail_icon),
                        contentDescription = ""
                    )

                }
            if (jobApps.work_experience.isNotEmpty())
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = stringResource(R.string.years_of_experience),
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp
                    )
                    Text(
                        text = jobApps.work_experience[0].years_of_experience.toString() +
                                " " + stringResource(R.string.years),
                        fontWeight = FontWeight.Normal,
                        fontSize = 16.sp
                    )
                }
        }
    }
}


@Composable
fun PersonExperience(workExperience: List<WorkExperience>) {
    Card(
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier
            .fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = colorResource(id = R.color.white)),
        elevation = CardDefaults.cardElevation(6.dp)
    ) {

        Column(
            modifier =
            Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 16.dp)
        ) {
            repeat(workExperience.size) { innerIndex ->

                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = stringResource(R.string.work_experience),
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 16.sp
                )



                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp),
                    text = workExperience[innerIndex].job_title,
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp
                )
                Text(
                    modifier = Modifier
                        .fillMaxWidth(),
                    text = workExperience[innerIndex].company_name,
                    fontSize = 12.sp
                )
                Text(
                    modifier = Modifier
                        .fillMaxWidth(),
                    text = workExperience[innerIndex].years_of_experience.toString(),
                    fontSize = 12.sp
                )

            }
        }
    }
}

@Composable
fun PersonSkills(skill: List<Skill>) {
    Card(
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier
            .fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = colorResource(id = R.color.white)),
        elevation = CardDefaults.cardElevation(6.dp)
    ) {
        Column(
            modifier =
            Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 16.dp)
        ) {
            Text(
                modifier = Modifier,
                text = stringResource(R.string.skills),
                fontWeight = FontWeight.SemiBold,
                fontSize = 16.sp
            )
            Column(
                modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp)
            ) {
                repeat(skill.size) { innerIndex ->
                    Row(
                        Modifier
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {

                        Text(
                            modifier = Modifier,
                            text = skill[innerIndex].name,
                            fontSize = 12.sp
                        )
                        ProgressBarWithPercentage(skill[innerIndex].progress)

                    }
                }
            }
        }
    }
}


@Composable
fun PersonRating(language: List<Language>) {
    Card(
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier
            .fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = colorResource(id = R.color.white)),
        elevation = CardDefaults.cardElevation(6.dp)
    ) {
        Column(
            modifier =
            Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 16.dp)
        ) {
            Text(
                modifier = Modifier,
                text = stringResource(id = R.string.language),
                fontWeight = FontWeight.SemiBold,
                fontSize = 16.sp
            )
            Column(modifier = Modifier.padding(top = 8.dp)) {
                repeat(language.size) { innerIndex ->

                    Row(
                        Modifier
                            .fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            modifier = Modifier,
                            text = language[innerIndex].name,
                            fontSize = 12.sp
                        )
                        StarRating(language[innerIndex].proficiency, 5)
                    }
                }
            }
        }
    }
}

@Composable
fun PersonCV(jobApps: Jobapps) {
    Card(
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier
            .fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = colorResource(id = R.color.white)),
        elevation = CardDefaults.cardElevation(6.dp)
    ) {
        val context = LocalContext.current
        Column(
            modifier =
            Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 16.dp)
        ) {
            Text(
                modifier = Modifier,
                text = "CV",
                fontWeight = FontWeight.SemiBold,
                fontSize = 15.sp
            )


            if (jobApps.is_locked == 1) {
                Column(
                    Modifier
                        .fillMaxWidth()
                        .padding(top = 4.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    Text(
                        modifier = Modifier,
                        text = "CV is Locked,Unlocked to view ",
                        color = Color.Red,
                        fontSize = 13.sp
                    )
                    Text(
                        modifier = Modifier.padding(top = 8.dp),
                        text = "Unlocked Candidate",
                        fontSize = 13.sp,
                        color = Color.Blue,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            } else {
                Button(modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp)
                    .align(Alignment.CenterHorizontally),
                    shape = RoundedCornerShape(15.dp),
                    colors = ButtonDefaults.buttonColors(colorResource(id = R.color.blue2)),
                    onClick = {
                        val url =
                            BASE_URL + "applicant/" + jobApps.cv
                        val file = "downloadedFile.pdf"
                        downloadFile(context, url, file)
                    }) {
                    Text(
                        text = stringResource(R.string.open_cv) ,
                        modifier = Modifier.padding(vertical = 8.dp),
                        style = TextStyle(textAlign = TextAlign.Center, fontSize = 15.sp)
                    )
                }
            }

        }
    }
}

@Composable
fun StarRating(rating: Float, maxRating: Int = 5) {
    Row {
        for (i in 1..maxRating) {
            val icon = when {
                i <= rating -> Icons.Filled.Star        // Fully filled star
                i - 1 < rating && rating < i -> Icons.Filled.StarHalf // Half-filled star
                else -> Icons.Outlined.Star             // Empty star
            }

            val starColor =
                if (i <= rating) Color.Yellow else Color.Gray // Ensure unfilled stars are gray

            Icon(
                imageVector = icon,
                contentDescription = "Star $i",
                tint = starColor,
                modifier = Modifier.size(24.dp)
            )
        }
    }
}


@Composable
fun EmptyText(message: String) {
    Text(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 14.dp),
        text = message,
        color = Color.Gray,
        fontSize = 14.sp,
        textAlign = TextAlign.Center
    )
}


@Composable
fun Education(education: Education) {
    Column(
        modifier = Modifier
            .padding(horizontal = 4.dp)
            .padding(top = 4.dp)
    ) {
        education.education?.let {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = it,
                fontWeight = FontWeight.SemiBold,
                fontSize = 15.sp
            )
        }
        education.university?.let {
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 4.dp),
                text = it,
                fontSize = 12.sp
            )
        }
        education.grad_year?.let {
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 4.dp),
                text = it,
                fontSize = 12.sp
            )
        }
    }
}

@Preview
@Composable
fun PreviewStarRating() {
    StarRating(rating = 4f)
}

@Composable
fun ProgressBarWithPercentage(progress: Float) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        LinearProgressIndicator(
            progress = progress / 100f,
            modifier = Modifier
                .width(150.dp)
                .height(6.dp),
            color = colorResource(id = R.color.blue),
            trackColor = Color.LightGray
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = "${progress.toInt()}%",
            color = Color.Gray,
            fontSize = 14.sp
        )
    }
}

@Preview
@Composable
fun PreviewProgressBarWithPercentage() {
    ProgressBarWithPercentage(progress = 50f)
}

fun downloadFile(context: Context, url: String, fileName: String) {
    val request = DownloadManager.Request(Uri.parse(url)).apply {
        setTitle("Downloading $fileName")
        setDescription("Downloading file...")
        setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
        setDestinationInExternalPublicDir(
            Environment.DIRECTORY_DOWNLOADS,
            fileName
        ) // Save in Downloads folder
        setAllowedOverMetered(true)  // Allow mobile data downloads
        setAllowedOverRoaming(true)  // Allow download while roaming
    }

    val downloadManager = context.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
    downloadManager.enqueue(request)
}