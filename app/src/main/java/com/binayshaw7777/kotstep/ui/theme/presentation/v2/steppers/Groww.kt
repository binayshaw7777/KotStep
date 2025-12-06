package com.binayshaw7777.kotstep.ui.theme.presentation.v2.steppers

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.Help
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.binayshaw7777.kotstep.ui.theme.GROWW_BACKGROUND
import com.binayshaw7777.kotstep.ui.theme.GROWW_GREEN
import com.binayshaw7777.kotstep.v3.KotStep
import com.binayshaw7777.kotstep.v3.model.style.KotStepStyle
import com.binayshaw7777.kotstep.v3.model.style.LineStyle
import com.binayshaw7777.kotstep.v3.model.style.LineStyles
import com.binayshaw7777.kotstep.v3.model.style.StepStyle
import com.binayshaw7777.kotstep.v3.model.style.StepStyles
import com.binayshaw7777.kotstep.v3.util.ExperimentalKotStep

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GrowwPreview(modifier: Modifier = Modifier) {
    val context = LocalContext.current

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Groww", color = Color.White) },
                navigationIcon = {
                    IconButton(onClick = {
                        Toast.makeText(
                            context,
                            "Click on the top right 3 dots.",
                            Toast.LENGTH_SHORT
                        ).show()
                    }) {
                        Icon(
                            Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back",
                            tint = Color.White
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = GROWW_BACKGROUND)
            )
        }
    ) { paddingValues ->
        GrowwContent(modifier, paddingValues)
    }
}

@Composable
fun GrowwContent(modifier: Modifier = Modifier, paddingValues: PaddingValues) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(paddingValues)
            .background(GROWW_BACKGROUND)
            .padding(vertical = 20.dp)
            .then(modifier)
    ) {
        Column(
            modifier = Modifier.padding(horizontal = 16.dp)
        ) {
            Icon(
                imageVector = Icons.Filled.Check,
                tint = GROWW_BACKGROUND,
                contentDescription = null,
                modifier = Modifier
                    .size(28.dp)
                    .drawBehind {
                        drawCircle(
                            color = GROWW_GREEN,
                            radius = this.size.minDimension / 2
                        )
                    }
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "₹15,000.00", style = TextStyle(
                    fontWeight = FontWeight.Bold,
                    fontSize = 24.sp,
                    color = Color.White
                )
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "New SIP   ∙   Completed", style = TextStyle(
                    fontWeight = FontWeight.Bold,
                    fontSize = 10.sp,
                    color = Color.LightGray
                )
            )

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.padding(top = 16.dp, bottom = 8.dp)
            ) {
                Text(
                    text = "GAMIX7 Mid Cap Opportunities Direct Plan Growth", style = TextStyle(
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 12.sp,
                        color = Color.LightGray
                    )
                )

                Icon(
                    imageVector = Icons.Default.ChevronRight,
                    contentDescription = null,
                    tint = Color.LightGray,
                    modifier = Modifier.size(16.dp)
                )
            }
        }

        HorizontalDivider(color = Color.DarkGray, modifier = Modifier.padding(vertical = 24.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            GrowwSideCard(
                modifier = Modifier.weight(1f),
                title = "Completed on",
                text = "02 Aug '24"
            )
            VerticalDivider(
                modifier = Modifier
                    .height(48.dp)
                    .padding(horizontal = 16.dp)
            )
            GrowwSideCard(modifier = Modifier.weight(1f), title = "NAV Date", text = "01 Aug '24")
        }

        HorizontalDivider(color = Color.DarkGray, modifier = Modifier.padding(vertical = 24.dp))

        Text(
            text = "Status",
            style = TextStyle(
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp,
                color = Color.White
            ),
            modifier = Modifier.padding(horizontal = 16.dp)
        )

        NewStyle()


        HorizontalDivider(color = Color.DarkGray, modifier = Modifier.padding(vertical = 24.dp))

        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(horizontal = 16.dp)
        ) {
            Text(
                text = "Details", style = TextStyle(
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp,
                    color = Color.White
                )
            )

            Spacer(modifier = Modifier.weight(1f))

            Icon(
                imageVector = Icons.Default.KeyboardArrowDown,
                tint = Color.White,
                contentDescription = null,
                modifier = Modifier.size(20.dp)
            )
        }

        HorizontalDivider(color = Color.DarkGray, modifier = Modifier.padding(vertical = 24.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(horizontal = 16.dp).padding(bottom = 20.dp)
        ) {
            Icon(
                imageVector = Icons.Filled.Help,
                tint = Color.LightGray,
                contentDescription = null,
            )
            Text(
                text = "Need Help?", style = TextStyle(
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp,
                    color = Color.White
                ),
                modifier = Modifier.padding(start = 8.dp)
            )
            Spacer(modifier = Modifier.weight(1f))
            Icon(
                imageVector = Icons.Default.KeyboardArrowRight,
                tint = Color.White,
                contentDescription = null,
                modifier = Modifier.size(16.dp)
            )
        }
    }
}

@Composable
fun GrowwSideCard(
    modifier: Modifier = Modifier,
    title: String,
    text: String
) {
    Column(
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.spacedBy(4.dp),
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .then(modifier)
    ) {
        Text(
            text = title, style = TextStyle(
                fontWeight = FontWeight.SemiBold,
                fontSize = 12.sp,
                color = Color.LightGray
            )
        )
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = text, style = TextStyle(
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp,
                    color = Color.White
                )
            )
            Icon(
                imageVector = Icons.Outlined.Info,
                tint = Color.White,
                contentDescription = null,
                modifier = Modifier.size(16.dp)
            )
        }
    }
}

@OptIn(ExperimentalKotStep::class)
private fun getKotStepStyle(): KotStepStyle {
    // TODO: Only pass what is needed rest in StepStyles should be by default set
    return KotStepStyle(
        stepStyle = StepStyles.default(
            onDone = StepStyle(
                stepColor = GROWW_GREEN
            )
        ),
        lineStyle = LineStyles.default(
            onDone = LineStyle(
                progressColor = Color.DarkGray,
                lineThickness = 1.dp,
                lineLength = 36.dp,
                linePadding = PaddingValues(vertical = 5.dp)
            )
        )
    )
}

@Composable
private fun GrowwLabelComposable(
    primaryText: String,
    secondaryText: String
) {
    Column(
        modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Text(
            text = primaryText,
            style = TextStyle(
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp,
                color = Color.White
            ),
        )
        Text(
            text = secondaryText,
            style = TextStyle(
                fontWeight = FontWeight.Medium,
                fontSize = 12.sp,
                color = Color.Gray
            ),
        )
    }
}

@OptIn(ExperimentalKotStep::class)
@Composable
private fun NewStyle() {
    KotStep(
        modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
        style = getKotStepStyle(),
        currentStep = { 3f }
    ) {
        step(
            icon = Icons.Default.Check,
            label = {
                GrowwLabelComposable("Order approved by exchange", "02 Aug, '24, 09:00 AM")
            },
            isCollapsible = true
        )
        step(
            icon = Icons.Default.Check,
            label = {
                GrowwLabelComposable("Auto-payment confirmed", "02 Aug, '24, 09:00 AM")
            },
            isCollapsible = true
        )
        step(
            icon = Icons.Default.Check,
            label = {
                GrowwLabelComposable("Units allocated", "02 Aug, '24, 09:00 AM")
            },
            isCollapsible = true
        )
    }
}