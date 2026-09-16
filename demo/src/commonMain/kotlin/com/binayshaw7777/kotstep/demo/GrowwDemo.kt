package com.binayshaw7777.kotstep.demo

import androidx.compose.animation.AnimatedVisibility
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.binayshaw7777.kotstep.v3.KotStep
import com.binayshaw7777.kotstep.v3.model.step.StepLayoutStyle
import com.binayshaw7777.kotstep.v3.model.style.KotStepStyle
import com.binayshaw7777.kotstep.v3.model.style.LineStyle
import com.binayshaw7777.kotstep.v3.model.style.LineStyles
import com.binayshaw7777.kotstep.v3.model.style.StepStyle
import com.binayshaw7777.kotstep.v3.model.style.StepStyles
import com.binayshaw7777.kotstep.v3.util.ExperimentalKotStep
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class, ExperimentalKotStep::class)
@Composable
fun GrowwDemo(
    modifier: Modifier = Modifier,
    onBack: (() -> Unit)? = null
) {
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    var detailsExpanded by remember { mutableStateOf(false) }

    Scaffold(
        modifier = modifier,
        snackbarHost = { SnackbarHost(snackbarHostState) },
        topBar = {
            TopAppBar(
                title = { Text(text = "Groww", color = Color.White, fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    if (onBack != null) {
                        IconButton(onClick = onBack) {
                            Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back", tint = Color.White)
                        }
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = GROWW_BACKGROUND)
            )
        },
        containerColor = GROWW_BACKGROUND
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 20.dp, vertical = 16.dp)
        ) {
            // Success Indicator Icon
            Icon(
                imageVector = Icons.Filled.Check,
                tint = GROWW_BACKGROUND,
                contentDescription = null,
                modifier = Modifier
                    .size(32.dp)
                    .drawBehind {
                        drawCircle(
                            color = GROWW_GREEN,
                            radius = this.size.minDimension / 2
                        )
                    }
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Investment Amount
            Text(
                text = "₹15,000.00",
                style = TextStyle(
                    fontWeight = FontWeight.Bold,
                    fontSize = 28.sp,
                    color = Color.White
                )
            )

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = "New SIP  ∙  Completed",
                style = TextStyle(
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 12.sp,
                    color = GROWW_TEXT_SECONDARY
                )
            )

            Spacer(modifier = Modifier.height(14.dp))

            // Fund Name Card
            Card(
                colors = CardDefaults.cardColors(containerColor = GROWW_SURFACE),
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier.fillMaxWidth().noRippleClick {
                    scope.launch {
                        snackbarHostState.showSnackbar("GAMIX7 Mid Cap Opportunities Fund selected")
                    }
                }
            ) {
                Row(
                    modifier = Modifier.padding(14.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "GAMIX7 Mid Cap Opportunities Direct Plan Growth",
                        style = TextStyle(
                            fontWeight = FontWeight.Medium,
                            fontSize = 13.sp,
                            color = Color.White
                        ),
                        modifier = Modifier.weight(1f)
                    )
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                        contentDescription = null,
                        tint = GROWW_TEXT_SECONDARY,
                        modifier = Modifier.size(18.dp)
                    )
                }
            }

            HorizontalDivider(color = GROWW_DIVIDER, modifier = Modifier.padding(vertical = 20.dp))

            // Summary Meta Row
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                GrowwInfoColumn(
                    modifier = Modifier.weight(1f),
                    title = "Completed on",
                    text = "02 Aug '24"
                )
                VerticalDivider(
                    modifier = Modifier
                        .height(44.dp)
                        .padding(horizontal = 12.dp),
                    color = GROWW_DIVIDER
                )
                GrowwInfoColumn(
                    modifier = Modifier.weight(1f),
                    title = "NAV Date",
                    text = "01 Aug '24"
                )
            }

            HorizontalDivider(color = GROWW_DIVIDER, modifier = Modifier.padding(vertical = 20.dp))

            // Stepper Status Header
            Text(
                text = "Status",
                style = TextStyle(
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    color = Color.White
                ),
                modifier = Modifier.padding(bottom = 12.dp)
            )

            // KotStep V3 Vertical Timeline Stepper
            KotStep(
                modifier = Modifier.padding(vertical = 4.dp),
                style = getGrowwKotStepStyle(),
                currentStep = { 3f }
            ) {
                step(
                    icon = Icons.Default.Check,
                    trailingLabel = {
                        GrowwStepLabel(
                            primary = "Order approved by exchange",
                            secondary = "02 Aug '24, 09:00 AM"
                        )
                    },
                    isCollapsible = true
                )
                step(
                    icon = Icons.Default.Check,
                    trailingLabel = {
                        GrowwStepLabel(
                            primary = "Auto-payment confirmed",
                            secondary = "02 Aug '24, 09:00 AM"
                        )
                    },
                    isCollapsible = true
                )
                step(
                    icon = Icons.Default.Check,
                    trailingLabel = {
                        GrowwStepLabel(
                            primary = "Units allocated",
                            secondary = "02 Aug '24, 09:00 AM"
                        )
                    },
                    isCollapsible = true
                )
            }

            HorizontalDivider(color = GROWW_DIVIDER, modifier = Modifier.padding(vertical = 20.dp))

            // Expandable Details Section
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .noRippleClick { detailsExpanded = !detailsExpanded },
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Details",
                    style = TextStyle(
                        fontWeight = FontWeight.Bold,
                        fontSize = 15.sp,
                        color = Color.White
                    )
                )
                Icon(
                    imageVector = if (detailsExpanded) Icons.Default.KeyboardArrowDown else Icons.AutoMirrored.Filled.KeyboardArrowRight,
                    tint = Color.White,
                    contentDescription = null,
                    modifier = Modifier.size(20.dp)
                )
            }

            AnimatedVisibility(visible = detailsExpanded) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 12.dp)
                        .background(GROWW_SURFACE, RoundedCornerShape(8.dp))
                        .padding(14.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    DetailRow("Folio Number", "1029384756")
                    DetailRow("NAV Price", "₹87.45")
                    DetailRow("Units Purchased", "171.526")
                    DetailRow("Transaction ID", "SIP-20240802-8841")
                }
            }

            HorizontalDivider(color = GROWW_DIVIDER, modifier = Modifier.padding(vertical = 20.dp))

            // Help Row
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .noRippleClick {
                        scope.launch {
                            snackbarHostState.showSnackbar("Support center is available 24/7.")
                        }
                    }
                    .padding(bottom = 24.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Info,
                    tint = GROWW_TEXT_SECONDARY,
                    contentDescription = null
                )
                Text(
                    text = "Need Help?",
                    style = TextStyle(
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 14.sp,
                        color = Color.White
                    ),
                    modifier = Modifier.padding(start = 10.dp)
                )
                Spacer(modifier = Modifier.weight(1f))
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                    tint = GROWW_TEXT_SECONDARY,
                    contentDescription = null,
                    modifier = Modifier.size(18.dp)
                )
            }
        }
    }
}

@Composable
private fun DetailRow(label: String, value: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = label, color = GROWW_TEXT_SECONDARY, fontSize = 12.sp)
        Text(text = value, color = Color.White, fontSize = 12.sp, fontWeight = FontWeight.Medium)
    }
}

@Composable
private fun GrowwInfoColumn(
    modifier: Modifier = Modifier,
    title: String,
    text: String
) {
    Column(
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.spacedBy(4.dp),
        modifier = modifier
    ) {
        Text(
            text = title,
            style = TextStyle(
                fontWeight = FontWeight.Medium,
                fontSize = 12.sp,
                color = GROWW_TEXT_SECONDARY
            )
        )
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            Text(
                text = text,
                style = TextStyle(
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp,
                    color = Color.White
                )
            )
            Icon(
                imageVector = Icons.Default.Info,
                tint = GROWW_TEXT_SECONDARY,
                contentDescription = null,
                modifier = Modifier.size(14.dp)
            )
        }
    }
}

@Composable
private fun GrowwStepLabel(
    primary: String,
    secondary: String
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 14.dp),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.spacedBy(2.dp)
    ) {
        Text(
            text = primary,
            style = TextStyle(
                fontWeight = FontWeight.SemiBold,
                fontSize = 13.sp,
                color = Color.White
            )
        )
        Text(
            text = secondary,
            style = TextStyle(
                fontWeight = FontWeight.Normal,
                fontSize = 11.sp,
                color = GROWW_TEXT_SECONDARY
            )
        )
    }
}

@OptIn(ExperimentalKotStep::class)
private fun getGrowwKotStepStyle(): KotStepStyle {
    return KotStepStyle(
        stepLayoutStyle = StepLayoutStyle.Vertical,
        stepStyle = StepStyles.default().copy(
            onDone = StepStyle(
                stepColor = GROWW_GREEN,
                stepSize = 24.dp
            )
        ),
        lineStyle = LineStyles.default().copy(
            onDone = LineStyle(
                progressColor = Color.DarkGray,
                lineThickness = 2.dp,
                lineLength = 42.dp,
                linePadding = PaddingValues(vertical = 4.dp)
            )
        )
    )
}
