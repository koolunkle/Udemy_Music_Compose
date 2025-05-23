package com.example.musicui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun SubscriptionScreen() {
    Column(
        modifier = Modifier.heightIn(max = 200.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(text = "Manage Subscription")
        Card(
            modifier = Modifier.padding(8.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        ) {
            Column(modifier = Modifier.padding(8.dp)) {
                Column {
                    Text(text = "Musical")
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                    ) {
                        Text(text = "Free Tier")
                        TextButton(onClick = {}) {
                            Row {
                                Text(text = "See All Plans")
                                Icon(
                                    imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                                    contentDescription = "See All Plans"
                                )
                            }
                        }
                    }
                }
                Divider(
                    color = Color.White,
                    thickness = 1.dp,
                    modifier = Modifier.padding(horizontal = 8.dp)
                )
                Row(modifier = Modifier.padding(vertical = 16.dp)) {
                    Icon(
                        imageVector = Icons.Default.AccountBox,
                        contentDescription = "Get a Plan"
                    )
                    Text(text = "Get a Plan")
                }
            }
        }
    }
}