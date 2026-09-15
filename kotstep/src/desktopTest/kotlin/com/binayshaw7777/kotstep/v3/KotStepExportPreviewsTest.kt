package com.binayshaw7777.kotstep.v3

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ImageComposeScene
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.binayshaw7777.kotstep.v3.model.step.StepLayoutStyle
import com.binayshaw7777.kotstep.v3.model.style.BorderStyle
import com.binayshaw7777.kotstep.v3.model.style.IconStyle
import com.binayshaw7777.kotstep.v3.model.style.KotStepStyle
import com.binayshaw7777.kotstep.v3.model.style.LineStyle
import com.binayshaw7777.kotstep.v3.model.style.LineStyles
import com.binayshaw7777.kotstep.v3.model.style.LineType
import com.binayshaw7777.kotstep.v3.model.style.StepStyle
import com.binayshaw7777.kotstep.v3.model.style.StepStyles
import com.binayshaw7777.kotstep.v3.util.ExperimentalKotStep
import org.jetbrains.skia.EncodedImageFormat
import java.io.File
import kotlin.test.Test

@OptIn(ExperimentalKotStep::class)
class KotStepExportPreviewsTest {

    private fun renderToFile(
        width: Int,
        height: Int,
        outFile: File,
        content: @Composable () -> Unit
    ) {
        outFile.parentFile?.mkdirs()
        val scene = ImageComposeScene(width = width, height = height) {
            content()
        }
        var image = scene.render()
        var time = 0L
        for (i in 1..10) {
            androidx.compose.runtime.snapshots.Snapshot.sendApplyNotifications()
            time += 16_000_000L
            image = scene.render(time)
        }
        val data = image.encodeToData(EncodedImageFormat.PNG)
            ?: error("Failed to encode PNG for ${outFile.name}")
        outFile.writeBytes(data.bytes)
        println("Exported preview: ${outFile.absolutePath} (${data.bytes.size} bytes)")
    }

    @Test
    fun exportAllPreviews() {
        val outDir = File("../docs/images").canonicalFile
        outDir.mkdirs()

        // 1. Horizontal Stepper
        renderToFile(760, 160, File(outDir, "horizontal_stepper.png")) {
            MaterialTheme(colorScheme = lightColorScheme()) {
                Surface(
                    color = Color.White,
                    modifier = Modifier.size(760.dp, 160.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp)
                            .background(Color(0xFFF8FAFC), RoundedCornerShape(16.dp))
                            .border(1.dp, Color(0xFFE2E8F0), RoundedCornerShape(16.dp))
                            .padding(horizontal = 40.dp, vertical = 20.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        KotStep(
                            currentStep = { 1.5f },
                            style = KotStepStyle(
                                stepLayoutStyle = StepLayoutStyle.Horizontal,
                                stepStyle = StepStyles.default().copy(
                                    onTodo = StepStyle(
                                        stepSize = 36.dp,
                                        stepColor = Color(0xFFE2E8F0),
                                        textStyle = androidx.compose.ui.text.TextStyle(color = Color(0xFF64748B), fontWeight = FontWeight.Bold, fontSize = 14.sp)
                                    ),
                                    onCurrent = StepStyle(
                                        stepSize = 40.dp,
                                        stepColor = Color(0xFF2563EB),
                                        textStyle = androidx.compose.ui.text.TextStyle(color = Color.White, fontWeight = FontWeight.Bold, fontSize = 14.sp),
                                        borderStyle = BorderStyle(width = 3.dp, color = Color(0xFF93C5FD))
                                    ),
                                    onDone = StepStyle(
                                        stepSize = 36.dp,
                                        stepColor = Color(0xFF10B981),
                                        textStyle = androidx.compose.ui.text.TextStyle(color = Color.White, fontWeight = FontWeight.Bold, fontSize = 14.sp)
                                    )
                                ),
                                lineStyle = LineStyles.default().copy(
                                    onTodo = LineStyle(
                                        lineLength = 130.dp,
                                        lineThickness = 4.dp,
                                        lineColor = Color(0xFFCBD5E1),
                                        lineStrokeCap = StrokeCap.Round
                                    ),
                                    onCurrent = LineStyle(
                                        lineLength = 130.dp,
                                        lineThickness = 4.dp,
                                        lineColor = Color(0xFFE2E8F0),
                                        progressColor = Color(0xFF2563EB),
                                        lineStrokeCap = StrokeCap.Round,
                                        progressStrokeCap = StrokeCap.Round
                                    ),
                                    onDone = LineStyle(
                                        lineLength = 130.dp,
                                        lineThickness = 4.dp,
                                        lineColor = Color(0xFF10B981),
                                        progressColor = Color(0xFF10B981),
                                        lineStrokeCap = StrokeCap.Round
                                    )
                                )
                            )
                        ) {
                            step(
                                title = "1",
                                trailingLabel = {
                                    Text("Cart", fontWeight = FontWeight.SemiBold, fontSize = 13.sp, color = Color(0xFF0F172A), modifier = Modifier.padding(top = 6.dp))
                                }
                            )
                            step(
                                title = "2",
                                trailingLabel = {
                                    Text("Shipping", fontWeight = FontWeight.Bold, fontSize = 13.sp, color = Color(0xFF2563EB), modifier = Modifier.padding(top = 6.dp))
                                }
                            )
                            step(
                                title = "3",
                                trailingLabel = {
                                    Text("Payment", fontWeight = FontWeight.Medium, fontSize = 13.sp, color = Color(0xFF64748B), modifier = Modifier.padding(top = 6.dp))
                                }
                            )
                            step(
                                title = "4",
                                trailingLabel = {
                                    Text("Review", fontWeight = FontWeight.Medium, fontSize = 13.sp, color = Color(0xFF64748B), modifier = Modifier.padding(top = 6.dp))
                                }
                            )
                        }
                    }
                }
            }
        }

        // 2. Vertical Timeline Stepper
        renderToFile(640, 360, File(outDir, "vertical_timeline.png")) {
            MaterialTheme(colorScheme = lightColorScheme()) {
                Surface(
                    color = Color.White,
                    modifier = Modifier.size(640.dp, 360.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp)
                            .background(Color(0xFFF8FAFC), RoundedCornerShape(16.dp))
                            .border(1.dp, Color(0xFFE2E8F0), RoundedCornerShape(16.dp))
                            .padding(24.dp)
                    ) {
                        KotStep(
                            currentStep = { 2f },
                            style = KotStepStyle(
                                stepLayoutStyle = StepLayoutStyle.Vertical,
                                stepStyle = StepStyles.default().copy(
                                    onTodo = StepStyle(
                                        stepSize = 28.dp,
                                        stepColor = Color(0xFFCBD5E1),
                                        textStyle = androidx.compose.ui.text.TextStyle(color = Color.White, fontWeight = FontWeight.Bold, fontSize = 12.sp)
                                    ),
                                    onCurrent = StepStyle(
                                        stepSize = 32.dp,
                                        stepColor = Color(0xFF6366F1),
                                        textStyle = androidx.compose.ui.text.TextStyle(color = Color.White, fontWeight = FontWeight.Bold, fontSize = 13.sp),
                                        borderStyle = BorderStyle(width = 3.dp, color = Color(0xFFC7D2FE))
                                    ),
                                    onDone = StepStyle(
                                        stepSize = 28.dp,
                                        stepColor = Color(0xFF10B981),
                                        textStyle = androidx.compose.ui.text.TextStyle(color = Color.White, fontWeight = FontWeight.Bold, fontSize = 12.sp)
                                    )
                                ),
                                lineStyle = LineStyles.default().copy(
                                    onTodo = LineStyle(
                                        lineLength = 32.dp,
                                        lineThickness = 3.dp,
                                        lineColor = Color(0xFFE2E8F0),
                                        lineStrokeCap = StrokeCap.Round
                                    ),
                                    onCurrent = LineStyle(
                                        lineLength = 32.dp,
                                        lineThickness = 3.dp,
                                        lineColor = Color(0xFFE2E8F0),
                                        progressColor = Color(0xFF6366F1),
                                        lineStrokeCap = StrokeCap.Round
                                    ),
                                    onDone = LineStyle(
                                        lineLength = 32.dp,
                                        lineThickness = 3.dp,
                                        lineColor = Color(0xFF10B981),
                                        progressColor = Color(0xFF10B981),
                                        lineStrokeCap = StrokeCap.Round
                                    )
                                )
                            )
                        ) {
                            step(
                                title = "1",
                                leadingLabel = {
                                    Text("09:30 AM", fontSize = 12.sp, fontWeight = FontWeight.Medium, color = Color(0xFF64748B), modifier = Modifier.padding(end = 16.dp, top = 4.dp))
                                },
                                trailingLabel = {
                                    Column(modifier = Modifier.padding(start = 14.dp)) {
                                        Text("Order Placed", fontWeight = FontWeight.Bold, fontSize = 14.sp, color = Color(0xFF0F172A))
                                        Text("Your order #89201 has been confirmed", fontSize = 12.sp, color = Color(0xFF64748B))
                                    }
                                }
                            )
                            step(
                                title = "2",
                                leadingLabel = {
                                    Text("11:15 AM", fontSize = 12.sp, fontWeight = FontWeight.Medium, color = Color(0xFF64748B), modifier = Modifier.padding(end = 16.dp, top = 4.dp))
                                },
                                trailingLabel = {
                                    Column(modifier = Modifier.padding(start = 14.dp)) {
                                        Text("Package Packed", fontWeight = FontWeight.Bold, fontSize = 14.sp, color = Color(0xFF0F172A))
                                        Text("Fulfilled by seller hub warehouse", fontSize = 12.sp, color = Color(0xFF64748B))
                                    }
                                }
                            )
                            step(
                                title = "3",
                                leadingLabel = {
                                    Text("02:45 PM", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = Color(0xFF6366F1), modifier = Modifier.padding(end = 16.dp, top = 4.dp))
                                },
                                trailingLabel = {
                                    Column(modifier = Modifier.padding(start = 14.dp)) {
                                        Text("Out for Delivery", fontWeight = FontWeight.Bold, fontSize = 14.sp, color = Color(0xFF6366F1))
                                        Text("Courier agent on the way to your address", fontSize = 12.sp, color = Color(0xFF64748B))
                                    }
                                }
                            )
                            step(
                                title = "4",
                                leadingLabel = {
                                    Text("Estimated", fontSize = 12.sp, fontWeight = FontWeight.Medium, color = Color(0xFF94A3B8), modifier = Modifier.padding(end = 16.dp, top = 4.dp))
                                },
                                trailingLabel = {
                                    Column(modifier = Modifier.padding(start = 14.dp)) {
                                        Text("Delivered", fontWeight = FontWeight.SemiBold, fontSize = 14.sp, color = Color(0xFF94A3B8))
                                        Text("Pending signature upon receipt", fontSize = 12.sp, color = Color(0xFF94A3B8))
                                    }
                                }
                            )
                        }
                    }
                }
            }
        }

        // 3. Custom Icons Stepper
        renderToFile(760, 160, File(outDir, "custom_icons.png")) {
            MaterialTheme(colorScheme = lightColorScheme()) {
                Surface(
                    color = Color.White,
                    modifier = Modifier.size(760.dp, 160.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp)
                            .background(Color(0xFFFAF5FF), RoundedCornerShape(16.dp))
                            .border(1.dp, Color(0xFFF3E8FF), RoundedCornerShape(16.dp))
                            .padding(horizontal = 40.dp, vertical = 20.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        KotStep(
                            currentStep = { 2f },
                            style = KotStepStyle(
                                stepLayoutStyle = StepLayoutStyle.Horizontal,
                                stepStyle = StepStyles.default().copy(
                                    onTodo = StepStyle(
                                        stepSize = 38.dp,
                                        stepColor = Color(0xFFE9D5FF),
                                        iconStyle = IconStyle(iconTint = Color(0xFF9333EA), iconSize = 18.dp)
                                    ),
                                    onCurrent = StepStyle(
                                        stepSize = 44.dp,
                                        stepColor = Color(0xFF9333EA),
                                        iconStyle = IconStyle(iconTint = Color.White, iconSize = 22.dp),
                                        borderStyle = BorderStyle(width = 3.dp, color = Color(0xFFD8B4FE))
                                    ),
                                    onDone = StepStyle(
                                        stepSize = 38.dp,
                                        stepColor = Color(0xFF7E22CE),
                                        iconStyle = IconStyle(iconTint = Color.White, iconSize = 18.dp)
                                    )
                                ),
                                lineStyle = LineStyles.default().copy(
                                    onTodo = LineStyle(
                                        lineLength = 130.dp,
                                        lineThickness = 4.dp,
                                        lineColor = Color(0xFFE9D5FF),
                                        lineStrokeCap = StrokeCap.Round
                                    ),
                                    onCurrent = LineStyle(
                                        lineLength = 130.dp,
                                        lineThickness = 4.dp,
                                        lineColor = Color(0xFFE9D5FF),
                                        progressColor = Color(0xFF9333EA),
                                        lineStrokeCap = StrokeCap.Round
                                    ),
                                    onDone = LineStyle(
                                        lineLength = 130.dp,
                                        lineThickness = 4.dp,
                                        lineColor = Color(0xFF7E22CE),
                                        progressColor = Color(0xFF7E22CE),
                                        lineStrokeCap = StrokeCap.Round
                                    )
                                )
                            )
                        ) {
                            step(
                                icon = Icons.Default.Home,
                                trailingLabel = {
                                    Text("Start", fontWeight = FontWeight.SemiBold, fontSize = 13.sp, color = Color(0xFF581C87), modifier = Modifier.padding(top = 6.dp))
                                }
                            )
                            step(
                                icon = Icons.Default.Person,
                                trailingLabel = {
                                    Text("Profile", fontWeight = FontWeight.SemiBold, fontSize = 13.sp, color = Color(0xFF581C87), modifier = Modifier.padding(top = 6.dp))
                                }
                            )
                            step(
                                icon = Icons.Default.Star,
                                trailingLabel = {
                                    Text("Perks", fontWeight = FontWeight.Bold, fontSize = 13.sp, color = Color(0xFF9333EA), modifier = Modifier.padding(top = 6.dp))
                                }
                            )
                            step(
                                icon = Icons.Default.Favorite,
                                trailingLabel = {
                                    Text("Done", fontWeight = FontWeight.Medium, fontSize = 13.sp, color = Color(0xFFA855F7), modifier = Modifier.padding(top = 6.dp))
                                }
                            )
                        }
                    }
                }
            }
        }

        // 4. Dashed Line Stepper with Rounded Rectangles
        renderToFile(760, 160, File(outDir, "dashed_line_stepper.png")) {
            MaterialTheme(colorScheme = lightColorScheme()) {
                Surface(
                    color = Color.White,
                    modifier = Modifier.size(760.dp, 160.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp)
                            .background(Color(0xFFFFFBEB), RoundedCornerShape(16.dp))
                            .border(1.dp, Color(0xFFFEF3C7), RoundedCornerShape(16.dp))
                            .padding(horizontal = 40.dp, vertical = 20.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        KotStep(
                            currentStep = { 1f },
                            style = KotStepStyle(
                                stepLayoutStyle = StepLayoutStyle.Horizontal,
                                stepStyle = StepStyles.default().copy(
                                    onTodo = StepStyle(
                                        stepSize = 36.dp,
                                        stepShape = RoundedCornerShape(10.dp),
                                        stepColor = Color(0xFFFDE68A),
                                        textStyle = androidx.compose.ui.text.TextStyle(color = Color(0xFF92400E), fontWeight = FontWeight.Bold, fontSize = 14.sp)
                                    ),
                                    onCurrent = StepStyle(
                                        stepSize = 40.dp,
                                        stepShape = RoundedCornerShape(12.dp),
                                        stepColor = Color(0xFFF59E0B),
                                        textStyle = androidx.compose.ui.text.TextStyle(color = Color.White, fontWeight = FontWeight.Bold, fontSize = 14.sp),
                                        borderStyle = BorderStyle(width = 2.dp, color = Color(0xFFB45309), shape = RoundedCornerShape(12.dp))
                                    ),
                                    onDone = StepStyle(
                                        stepSize = 36.dp,
                                        stepShape = RoundedCornerShape(10.dp),
                                        stepColor = Color(0xFFD97706),
                                        textStyle = androidx.compose.ui.text.TextStyle(color = Color.White, fontWeight = FontWeight.Bold, fontSize = 14.sp)
                                    )
                                ),
                                lineStyle = LineStyles.default().copy(
                                    onTodo = LineStyle(
                                        lineLength = 130.dp,
                                        lineThickness = 3.dp,
                                        lineColor = Color(0xFFFCD34D),
                                        lineType = LineType.Dashed(dashLength = 8.dp, gapLength = 6.dp)
                                    ),
                                    onCurrent = LineStyle(
                                        lineLength = 130.dp,
                                        lineThickness = 3.dp,
                                        lineColor = Color(0xFFFCD34D),
                                        progressColor = Color(0xFFF59E0B),
                                        lineType = LineType.Dashed(dashLength = 8.dp, gapLength = 6.dp),
                                        progressType = LineType.Dashed(dashLength = 8.dp, gapLength = 6.dp)
                                    ),
                                    onDone = LineStyle(
                                        lineLength = 130.dp,
                                        lineThickness = 3.dp,
                                        lineColor = Color(0xFFD97706),
                                        progressColor = Color(0xFFD97706),
                                        lineType = LineType.Dashed(dashLength = 8.dp, gapLength = 6.dp)
                                    )
                                )
                            )
                        ) {
                            step(
                                title = "A",
                                trailingLabel = {
                                    Text("Plan", fontWeight = FontWeight.SemiBold, fontSize = 13.sp, color = Color(0xFF78350F), modifier = Modifier.padding(top = 6.dp))
                                }
                            )
                            step(
                                title = "B",
                                trailingLabel = {
                                    Text("Build", fontWeight = FontWeight.Bold, fontSize = 13.sp, color = Color(0xFFB45309), modifier = Modifier.padding(top = 6.dp))
                                }
                            )
                            step(
                                title = "C",
                                trailingLabel = {
                                    Text("Test", fontWeight = FontWeight.Medium, fontSize = 13.sp, color = Color(0xFF92400E), modifier = Modifier.padding(top = 6.dp))
                                }
                            )
                            step(
                                title = "D",
                                trailingLabel = {
                                    Text("Deploy", fontWeight = FontWeight.Medium, fontSize = 13.sp, color = Color(0xFF92400E), modifier = Modifier.padding(top = 6.dp))
                                }
                            )
                        }
                    }
                }
            }
        }

        // 5. Dark Fintech Theme Stepper
        renderToFile(760, 220, File(outDir, "dark_fintech_stepper.png")) {
            MaterialTheme(colorScheme = darkColorScheme()) {
                Surface(
                    color = Color(0xFF0F172A),
                    modifier = Modifier.size(760.dp, 220.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp)
                            .background(Color(0xFF1E293B), RoundedCornerShape(16.dp))
                            .border(1.dp, Color(0xFF334155), RoundedCornerShape(16.dp))
                            .padding(24.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Row(
                                modifier = Modifier.fillMaxWidth().padding(bottom = 18.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text("KYC Identity Verification", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                                Text("Step 2 of 4", color = Color(0xFF38BDF8), fontWeight = FontWeight.SemiBold, fontSize = 13.sp)
                            }

                            KotStep(
                                currentStep = { 1.3f },
                                style = KotStepStyle(
                                    stepLayoutStyle = StepLayoutStyle.Horizontal,
                                    stepStyle = StepStyles.default().copy(
                                        onTodo = StepStyle(
                                            stepSize = 34.dp,
                                            stepColor = Color(0xFF334155),
                                            textStyle = androidx.compose.ui.text.TextStyle(color = Color(0xFF94A3B8), fontWeight = FontWeight.Bold, fontSize = 13.sp)
                                        ),
                                        onCurrent = StepStyle(
                                            stepSize = 38.dp,
                                            stepColor = Color(0xFF0284C7),
                                            textStyle = androidx.compose.ui.text.TextStyle(color = Color.White, fontWeight = FontWeight.Bold, fontSize = 13.sp),
                                            borderStyle = BorderStyle(width = 3.dp, color = Color(0xFF38BDF8))
                                        ),
                                        onDone = StepStyle(
                                            stepSize = 34.dp,
                                            stepColor = Color(0xFF10B981),
                                            textStyle = androidx.compose.ui.text.TextStyle(color = Color.White, fontWeight = FontWeight.Bold, fontSize = 13.sp)
                                        )
                                    ),
                                    lineStyle = LineStyles.default().copy(
                                        onTodo = LineStyle(
                                            lineLength = 130.dp,
                                            lineThickness = 4.dp,
                                            lineColor = Color(0xFF334155),
                                            lineStrokeCap = StrokeCap.Round
                                        ),
                                        onCurrent = LineStyle(
                                            lineLength = 130.dp,
                                            lineThickness = 4.dp,
                                            lineColor = Color(0xFF334155),
                                            progressColor = Color(0xFF0284C7),
                                            lineStrokeCap = StrokeCap.Round,
                                            progressStrokeCap = StrokeCap.Round
                                        ),
                                        onDone = LineStyle(
                                            lineLength = 130.dp,
                                            lineThickness = 4.dp,
                                            lineColor = Color(0xFF10B981),
                                            progressColor = Color(0xFF10B981),
                                            lineStrokeCap = StrokeCap.Round
                                        )
                                    )
                                )
                            ) {
                                step(
                                    title = "1",
                                    trailingLabel = {
                                        Text("Personal Info", fontWeight = FontWeight.Medium, fontSize = 12.sp, color = Color(0xFFE2E8F0), modifier = Modifier.padding(top = 6.dp))
                                    }
                                )
                                step(
                                    title = "2",
                                    trailingLabel = {
                                        Text("National ID", fontWeight = FontWeight.Bold, fontSize = 12.sp, color = Color(0xFF38BDF8), modifier = Modifier.padding(top = 6.dp))
                                    }
                                )
                                step(
                                    title = "3",
                                    trailingLabel = {
                                        Text("Facial Scan", fontWeight = FontWeight.Normal, fontSize = 12.sp, color = Color(0xFF64748B), modifier = Modifier.padding(top = 6.dp))
                                    }
                                )
                                step(
                                    title = "4",
                                    trailingLabel = {
                                        Text("Confirmation", fontWeight = FontWeight.Normal, fontSize = 12.sp, color = Color(0xFF64748B), modifier = Modifier.padding(top = 6.dp))
                                    }
                                )
                            }
                        }
                    }
                }
            }
        }

        // 6. Collapsible Details Stepper
        renderToFile(640, 360, File(outDir, "collapsible_stepper.png")) {
            MaterialTheme(colorScheme = lightColorScheme()) {
                Surface(
                    color = Color.White,
                    modifier = Modifier.size(640.dp, 360.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp)
                            .background(Color(0xFFF8FAFC), RoundedCornerShape(16.dp))
                            .border(1.dp, Color(0xFFE2E8F0), RoundedCornerShape(16.dp))
                            .padding(24.dp)
                    ) {
                        KotStep(
                            currentStep = { 1f },
                            style = KotStepStyle(
                                stepLayoutStyle = StepLayoutStyle.Vertical,
                                stepStyle = StepStyles.default().copy(
                                    onTodo = StepStyle(
                                        stepSize = 30.dp,
                                        stepColor = Color(0xFFCBD5E1),
                                        textStyle = androidx.compose.ui.text.TextStyle(color = Color.White, fontWeight = FontWeight.Bold, fontSize = 12.sp)
                                    ),
                                    onCurrent = StepStyle(
                                        stepSize = 32.dp,
                                        stepColor = Color(0xFF0D9488),
                                        textStyle = androidx.compose.ui.text.TextStyle(color = Color.White, fontWeight = FontWeight.Bold, fontSize = 13.sp),
                                        borderStyle = BorderStyle(width = 3.dp, color = Color(0xFF99F6E4))
                                    ),
                                    onDone = StepStyle(
                                        stepSize = 30.dp,
                                        stepColor = Color(0xFF10B981),
                                        textStyle = androidx.compose.ui.text.TextStyle(color = Color.White, fontWeight = FontWeight.Bold, fontSize = 12.sp)
                                    )
                                ),
                                lineStyle = LineStyles.default().copy(
                                    onTodo = LineStyle(
                                        lineLength = 40.dp,
                                        lineThickness = 3.dp,
                                        lineColor = Color(0xFFE2E8F0),
                                        lineStrokeCap = StrokeCap.Round
                                    ),
                                    onCurrent = LineStyle(
                                        lineLength = 40.dp,
                                        lineThickness = 3.dp,
                                        lineColor = Color(0xFFE2E8F0),
                                        progressColor = Color(0xFF0D9488),
                                        lineStrokeCap = StrokeCap.Round
                                    ),
                                    onDone = LineStyle(
                                        lineLength = 40.dp,
                                        lineThickness = 3.dp,
                                        lineColor = Color(0xFF10B981),
                                        progressColor = Color(0xFF10B981),
                                        lineStrokeCap = StrokeCap.Round
                                    )
                                )
                            )
                        ) {
                            step(
                                title = "1",
                                trailingLabel = {
                                    Column(modifier = Modifier.padding(start = 14.dp)) {
                                        Text("Account Setup", fontWeight = FontWeight.Bold, fontSize = 14.sp, color = Color(0xFF0F172A))
                                        Text("Completed on 14 Sep, 10:30 AM", fontSize = 12.sp, color = Color(0xFF64748B))
                                    }
                                }
                            )
                            step(
                                title = "2",
                                isCollapsible = true,
                                trailingLabel = {
                                    Column(modifier = Modifier.padding(start = 14.dp)) {
                                        Text("Subscription Plan (Expanded)", fontWeight = FontWeight.Bold, fontSize = 14.sp, color = Color(0xFF0D9488))
                                        Spacer(modifier = Modifier.height(4.dp))
                                        Box(
                                            modifier = Modifier
                                                .background(Color.White, RoundedCornerShape(8.dp))
                                                .border(1.dp, Color(0xFFCCFBF1), RoundedCornerShape(8.dp))
                                                .padding(10.dp)
                                        ) {
                                            Column {
                                                Text("Pro Plan — $19/month", fontWeight = FontWeight.SemiBold, fontSize = 12.sp, color = Color(0xFF0F172A))
                                                Text("Includes unlimited multiplatform steppers & themes", fontSize = 11.sp, color = Color(0xFF64748B))
                                            }
                                        }
                                    }
                                }
                            )
                            step(
                                title = "3",
                                trailingLabel = {
                                    Column(modifier = Modifier.padding(start = 14.dp)) {
                                        Text("Billing Information", fontWeight = FontWeight.Medium, fontSize = 14.sp, color = Color(0xFF94A3B8))
                                        Text("Next up after plan selection", fontSize = 12.sp, color = Color(0xFF94A3B8))
                                    }
                                }
                            )
                        }
                    }
                }
            }
        }

        // 7. Hero Showcase Banner (combines horizontal + vertical badges)
        renderToFile(920, 420, File(outDir, "kotstep_hero_banner.png")) {
            MaterialTheme(colorScheme = lightColorScheme()) {
                Surface(
                    color = Color(0xFFF1F5F9),
                    modifier = Modifier.size(920.dp, 420.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(20.dp)
                            .background(Color.White, RoundedCornerShape(20.dp))
                            .border(1.dp, Color(0xFFE2E8F0), RoundedCornerShape(20.dp))
                            .padding(24.dp),
                        verticalArrangement = Arrangement.SpaceBetween
                    ) {
                        // Header
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Text(
                                        text = "KotStep",
                                        fontWeight = FontWeight.Black,
                                        fontSize = 26.sp,
                                        color = Color(0xFF1E293B)
                                    )
                                    Spacer(modifier = Modifier.width(10.dp))
                                    Box(
                                        modifier = Modifier
                                            .background(Color(0xFFEFF6FF), RoundedCornerShape(6.dp))
                                            .border(1.dp, Color(0xFFBFDBFE), RoundedCornerShape(6.dp))
                                            .padding(horizontal = 8.dp, vertical = 2.dp)
                                    ) {
                                        Text("Compose Multiplatform", color = Color(0xFF2563EB), fontWeight = FontWeight.Bold, fontSize = 11.sp)
                                    }
                                }
                                Text(
                                    text = "Customizable Stepper UI for Android, iOS, Desktop & Web",
                                    color = Color(0xFF64748B),
                                    fontSize = 13.sp,
                                    modifier = Modifier.padding(top = 2.dp)
                                )
                            }

                            Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                                PlatformBadge("Android")
                                PlatformBadge("iOS")
                                PlatformBadge("Desktop")
                                PlatformBadge("Web")
                            }
                        }

                        HorizontalDivider(color = Color(0xFFF1F5F9), modifier = Modifier.padding(vertical = 12.dp))

                        // Stepper Preview Area
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(Color(0xFFF8FAFC), RoundedCornerShape(14.dp))
                                .border(1.dp, Color(0xFFE2E8F0), RoundedCornerShape(14.dp))
                                .padding(horizontal = 40.dp, vertical = 24.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            KotStep(
                                currentStep = { 2.4f },
                                style = KotStepStyle(
                                    stepLayoutStyle = StepLayoutStyle.Horizontal,
                                    stepStyle = StepStyles.default().copy(
                                        onTodo = StepStyle(
                                            stepSize = 36.dp,
                                            stepColor = Color(0xFFE2E8F0),
                                            textStyle = androidx.compose.ui.text.TextStyle(color = Color(0xFF64748B), fontWeight = FontWeight.Bold, fontSize = 13.sp)
                                        ),
                                        onCurrent = StepStyle(
                                            stepSize = 40.dp,
                                            stepColor = Color(0xFF4F46E5),
                                            textStyle = androidx.compose.ui.text.TextStyle(color = Color.White, fontWeight = FontWeight.Bold, fontSize = 14.sp),
                                            borderStyle = BorderStyle(width = 3.dp, color = Color(0xFFA5B4FC))
                                        ),
                                        onDone = StepStyle(
                                            stepSize = 36.dp,
                                            stepColor = Color(0xFF10B981),
                                            textStyle = androidx.compose.ui.text.TextStyle(color = Color.White, fontWeight = FontWeight.Bold, fontSize = 13.sp)
                                        )
                                    ),
                                    lineStyle = LineStyles.default().copy(
                                        onTodo = LineStyle(
                                            lineLength = 160.dp,
                                            lineThickness = 4.dp,
                                            lineColor = Color(0xFFCBD5E1),
                                            lineStrokeCap = StrokeCap.Round
                                        ),
                                        onCurrent = LineStyle(
                                            lineLength = 160.dp,
                                            lineThickness = 4.dp,
                                            lineColor = Color(0xFFE2E8F0),
                                            progressColor = Color(0xFF4F46E5),
                                            lineStrokeCap = StrokeCap.Round,
                                            progressStrokeCap = StrokeCap.Round
                                        ),
                                        onDone = LineStyle(
                                            lineLength = 160.dp,
                                            lineThickness = 4.dp,
                                            lineColor = Color(0xFF10B981),
                                            progressColor = Color(0xFF10B981),
                                            lineStrokeCap = StrokeCap.Round
                                        )
                                    )
                                )
                            ) {
                                step(
                                    title = "1",
                                    trailingLabel = {
                                        Text("Select Plan", fontWeight = FontWeight.SemiBold, fontSize = 12.sp, color = Color(0xFF1E293B), modifier = Modifier.padding(top = 6.dp))
                                    }
                                )
                                step(
                                    title = "2",
                                    trailingLabel = {
                                        Text("User Details", fontWeight = FontWeight.SemiBold, fontSize = 12.sp, color = Color(0xFF1E293B), modifier = Modifier.padding(top = 6.dp))
                                    }
                                )
                                step(
                                    title = "3",
                                    trailingLabel = {
                                        Text("Payment", fontWeight = FontWeight.Bold, fontSize = 12.sp, color = Color(0xFF4F46E5), modifier = Modifier.padding(top = 6.dp))
                                    }
                                )
                                step(
                                    title = "4",
                                    trailingLabel = {
                                        Text("Activation", fontWeight = FontWeight.Medium, fontSize = 12.sp, color = Color(0xFF64748B), modifier = Modifier.padding(top = 6.dp))
                                    }
                                )
                            }
                        }

                        // Footer feature tags
                        Row(
                            modifier = Modifier.fillMaxWidth().padding(top = 10.dp),
                            horizontalArrangement = Arrangement.SpaceEvenly
                        ) {
                            FeaturePill("⚡ Smooth Animations")
                            FeaturePill("🎨 Fully Customizable")
                            FeaturePill("📐 Horizontal & Vertical")
                            FeaturePill("🏷️ Leading & Trailing Slots")
                            FeaturePill("📦 Zero Android-leak")
                        }
                    }
                }
            }
        }

        // 8. Horizontal Dual Labels (Top Step Number + Bottom Label)
        renderToFile(760, 190, File(outDir, "horizontal_dual_labels.png")) {
            MaterialTheme(colorScheme = lightColorScheme()) {
                Surface(
                    color = Color.White,
                    modifier = Modifier.size(760.dp, 190.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp)
                            .background(Color(0xFFF8FAFC), RoundedCornerShape(16.dp))
                            .border(1.dp, Color(0xFFE2E8F0), RoundedCornerShape(16.dp))
                            .padding(horizontal = 36.dp, vertical = 18.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        KotStep(
                            currentStep = { 1.5f },
                            style = KotStepStyle(
                                stepLayoutStyle = StepLayoutStyle.Horizontal,
                                stepStyle = StepStyles.default().copy(
                                    onTodo = StepStyle(
                                        stepSize = 36.dp,
                                        stepColor = Color(0xFFE2E8F0),
                                        textStyle = androidx.compose.ui.text.TextStyle(color = Color(0xFF64748B), fontWeight = FontWeight.Bold, fontSize = 13.sp)
                                    ),
                                    onCurrent = StepStyle(
                                        stepSize = 40.dp,
                                        stepColor = Color(0xFF2563EB),
                                        textStyle = androidx.compose.ui.text.TextStyle(color = Color.White, fontWeight = FontWeight.Bold, fontSize = 14.sp),
                                        borderStyle = BorderStyle(width = 3.dp, color = Color(0xFF93C5FD))
                                    ),
                                    onDone = StepStyle(
                                        stepSize = 36.dp,
                                        stepColor = Color(0xFF10B981),
                                        textStyle = androidx.compose.ui.text.TextStyle(color = Color.White, fontWeight = FontWeight.Bold, fontSize = 13.sp)
                                    )
                                ),
                                lineStyle = LineStyles.default().copy(
                                    onTodo = LineStyle(lineLength = 130.dp, lineThickness = 4.dp, lineColor = Color(0xFFCBD5E1), lineStrokeCap = StrokeCap.Round),
                                    onCurrent = LineStyle(lineLength = 130.dp, lineThickness = 4.dp, lineColor = Color(0xFFE2E8F0), progressColor = Color(0xFF2563EB), lineStrokeCap = StrokeCap.Round, progressStrokeCap = StrokeCap.Round),
                                    onDone = LineStyle(lineLength = 130.dp, lineThickness = 4.dp, lineColor = Color(0xFF10B981), progressColor = Color(0xFF10B981), lineStrokeCap = StrokeCap.Round)
                                )
                            )
                        ) {
                            step(
                                title = "1",
                                leadingLabel = {
                                    Text("STEP 01", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = Color(0xFF10B981), modifier = Modifier.padding(bottom = 6.dp))
                                },
                                trailingLabel = {
                                    Text("Account", fontSize = 13.sp, fontWeight = FontWeight.SemiBold, color = Color(0xFF0F172A), modifier = Modifier.padding(top = 6.dp))
                                }
                            )
                            step(
                                title = "2",
                                leadingLabel = {
                                    Text("STEP 02", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = Color(0xFF2563EB), modifier = Modifier.padding(bottom = 6.dp))
                                },
                                trailingLabel = {
                                    Text("Profile", fontSize = 13.sp, fontWeight = FontWeight.Bold, color = Color(0xFF2563EB), modifier = Modifier.padding(top = 6.dp))
                                }
                            )
                            step(
                                title = "3",
                                leadingLabel = {
                                    Text("STEP 03", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = Color(0xFF94A3B8), modifier = Modifier.padding(bottom = 6.dp))
                                },
                                trailingLabel = {
                                    Text("Security", fontSize = 13.sp, fontWeight = FontWeight.Medium, color = Color(0xFF64748B), modifier = Modifier.padding(top = 6.dp))
                                }
                            )
                            step(
                                title = "4",
                                leadingLabel = {
                                    Text("STEP 04", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = Color(0xFF94A3B8), modifier = Modifier.padding(bottom = 6.dp))
                                },
                                trailingLabel = {
                                    Text("Review", fontSize = 13.sp, fontWeight = FontWeight.Medium, color = Color(0xFF64748B), modifier = Modifier.padding(top = 6.dp))
                                }
                            )
                        }
                    }
                }
            }
        }

        // 9. Horizontal Minimalist Dots (No text, compact onboarding pagination)
        renderToFile(540, 110, File(outDir, "horizontal_dots_minimal.png")) {
            MaterialTheme(colorScheme = lightColorScheme()) {
                Surface(
                    color = Color.White,
                    modifier = Modifier.size(540.dp, 110.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp)
                            .background(Color(0xFFF8FAFC), RoundedCornerShape(16.dp))
                            .border(1.dp, Color(0xFFE2E8F0), RoundedCornerShape(16.dp))
                            .padding(horizontal = 40.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        KotStep(
                            currentStep = { 2f },
                            style = KotStepStyle(
                                stepLayoutStyle = StepLayoutStyle.Horizontal,
                                showCheckMarkOnDone = false,
                                stepStyle = StepStyles.default().copy(
                                    onTodo = StepStyle(stepSize = 12.dp, stepColor = Color(0xFFCBD5E1)),
                                    onCurrent = StepStyle(
                                        stepSize = 16.dp,
                                        stepColor = Color(0xFF6366F1),
                                        borderStyle = BorderStyle(width = 3.dp, color = Color(0xFFC7D2FE))
                                    ),
                                    onDone = StepStyle(stepSize = 12.dp, stepColor = Color(0xFF10B981))
                                ),
                                lineStyle = LineStyles.default().copy(
                                    onTodo = LineStyle(lineLength = 64.dp, lineThickness = 3.dp, lineColor = Color(0xFFE2E8F0), lineStrokeCap = StrokeCap.Round),
                                    onCurrent = LineStyle(lineLength = 64.dp, lineThickness = 3.dp, lineColor = Color(0xFFE2E8F0), progressColor = Color(0xFF6366F1), lineStrokeCap = StrokeCap.Round),
                                    onDone = LineStyle(lineLength = 64.dp, lineThickness = 3.dp, lineColor = Color(0xFF10B981), progressColor = Color(0xFF10B981), lineStrokeCap = StrokeCap.Round)
                                )
                            )
                        ) {
                            step()
                            step()
                            step()
                            step()
                            step()
                        }
                    }
                }
            }
        }

        // 10. Horizontal Dotted Line Stepper (Warm Sunset Palette)
        renderToFile(760, 160, File(outDir, "horizontal_dotted_line.png")) {
            MaterialTheme(colorScheme = lightColorScheme()) {
                Surface(
                    color = Color.White,
                    modifier = Modifier.size(760.dp, 160.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp)
                            .background(Color(0xFFFFF1F2), RoundedCornerShape(16.dp))
                            .border(1.dp, Color(0xFFFFE4E6), RoundedCornerShape(16.dp))
                            .padding(horizontal = 40.dp, vertical = 20.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        KotStep(
                            currentStep = { 1.5f },
                            style = KotStepStyle(
                                stepLayoutStyle = StepLayoutStyle.Horizontal,
                                stepStyle = StepStyles.default().copy(
                                    onTodo = StepStyle(
                                        stepSize = 36.dp,
                                        stepColor = Color(0xFFFFE4E6),
                                        textStyle = androidx.compose.ui.text.TextStyle(color = Color(0xFFFB7185), fontWeight = FontWeight.Bold, fontSize = 13.sp)
                                    ),
                                    onCurrent = StepStyle(
                                        stepSize = 42.dp,
                                        stepColor = Color(0xFFF43F5E),
                                        textStyle = androidx.compose.ui.text.TextStyle(color = Color.White, fontWeight = FontWeight.Bold, fontSize = 14.sp),
                                        borderStyle = BorderStyle(width = 3.dp, color = Color(0xFFFECDD3))
                                    ),
                                    onDone = StepStyle(
                                        stepSize = 36.dp,
                                        stepColor = Color(0xFFE11D48),
                                        textStyle = androidx.compose.ui.text.TextStyle(color = Color.White, fontWeight = FontWeight.Bold, fontSize = 13.sp)
                                    )
                                ),
                                lineStyle = LineStyles.default().copy(
                                    onTodo = LineStyle(
                                        lineLength = 130.dp,
                                        lineThickness = 4.dp,
                                        lineColor = Color(0xFFFECDD3),
                                        lineType = LineType.Dotted(gapLength = 8.dp),
                                        lineStrokeCap = StrokeCap.Round
                                    ),
                                    onCurrent = LineStyle(
                                        lineLength = 130.dp,
                                        lineThickness = 4.dp,
                                        lineColor = Color(0xFFFECDD3),
                                        progressColor = Color(0xFFF43F5E),
                                        lineType = LineType.Dotted(gapLength = 8.dp),
                                        lineStrokeCap = StrokeCap.Round,
                                        progressStrokeCap = StrokeCap.Round
                                    ),
                                    onDone = LineStyle(
                                        lineLength = 130.dp,
                                        lineThickness = 4.dp,
                                        lineColor = Color(0xFFE11D48),
                                        progressColor = Color(0xFFE11D48),
                                        lineType = LineType.Dotted(gapLength = 8.dp),
                                        lineStrokeCap = StrokeCap.Round
                                    )
                                )
                            )
                        ) {
                            step(
                                title = "1",
                                trailingLabel = {
                                    Text("Plan", fontWeight = FontWeight.SemiBold, fontSize = 13.sp, color = Color(0xFF881337), modifier = Modifier.padding(top = 6.dp))
                                }
                            )
                            step(
                                title = "2",
                                trailingLabel = {
                                    Text("Design", fontWeight = FontWeight.Bold, fontSize = 13.sp, color = Color(0xFFE11D48), modifier = Modifier.padding(top = 6.dp))
                                }
                            )
                            step(
                                title = "3",
                                trailingLabel = {
                                    Text("Develop", fontWeight = FontWeight.Medium, fontSize = 13.sp, color = Color(0xFF9F1239), modifier = Modifier.padding(top = 6.dp))
                                }
                            )
                            step(
                                title = "4",
                                trailingLabel = {
                                    Text("Ship", fontWeight = FontWeight.Medium, fontSize = 13.sp, color = Color(0xFF9F1239), modifier = Modifier.padding(top = 6.dp))
                                }
                            )
                        }
                    }
                }
            }
        }

        // 11. Horizontal Custom Composable Indicators (Emojis / Badges)
        renderToFile(760, 160, File(outDir, "horizontal_custom_content.png")) {
            MaterialTheme(colorScheme = lightColorScheme()) {
                Surface(
                    color = Color.White,
                    modifier = Modifier.size(760.dp, 160.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp)
                            .background(Color(0xFFF0FDF4), RoundedCornerShape(16.dp))
                            .border(1.dp, Color(0xFFDCFCE7), RoundedCornerShape(16.dp))
                            .padding(horizontal = 40.dp, vertical = 20.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        KotStep(
                            currentStep = { 2f },
                            style = KotStepStyle(
                                stepLayoutStyle = StepLayoutStyle.Horizontal,
                                showCheckMarkOnDone = false,
                                stepStyle = StepStyles.default().copy(
                                    onTodo = StepStyle(
                                        stepSize = 40.dp,
                                        stepColor = Color(0xFFDCFCE7),
                                        borderStyle = BorderStyle(width = 1.dp, color = Color(0xFFBBF7D0))
                                    ),
                                    onCurrent = StepStyle(
                                        stepSize = 46.dp,
                                        stepColor = Color(0xFF15803D),
                                        borderStyle = BorderStyle(width = 3.dp, color = Color(0xFF86EFAC))
                                    ),
                                    onDone = StepStyle(
                                        stepSize = 40.dp,
                                        stepColor = Color(0xFF16A34A)
                                    )
                                ),
                                lineStyle = LineStyles.default().copy(
                                    onTodo = LineStyle(lineLength = 130.dp, lineThickness = 4.dp, lineColor = Color(0xFFDCFCE7), lineStrokeCap = StrokeCap.Round),
                                    onCurrent = LineStyle(lineLength = 130.dp, lineThickness = 4.dp, lineColor = Color(0xFFDCFCE7), progressColor = Color(0xFF15803D), lineStrokeCap = StrokeCap.Round),
                                    onDone = LineStyle(lineLength = 130.dp, lineThickness = 4.dp, lineColor = Color(0xFF16A34A), progressColor = Color(0xFF16A34A), lineStrokeCap = StrokeCap.Round)
                                )
                            )
                        ) {
                            step(
                                content = { Text("🚀", fontSize = 16.sp) },
                                trailingLabel = {
                                    Text("Launch", fontWeight = FontWeight.SemiBold, fontSize = 13.sp, color = Color(0xFF166534), modifier = Modifier.padding(top = 6.dp))
                                }
                            )
                            step(
                                content = { Text("⚡", fontSize = 16.sp) },
                                trailingLabel = {
                                    Text("Compute", fontWeight = FontWeight.SemiBold, fontSize = 13.sp, color = Color(0xFF166534), modifier = Modifier.padding(top = 6.dp))
                                }
                            )
                            step(
                                content = { Text("🛡️", fontSize = 16.sp) },
                                trailingLabel = {
                                    Text("Security", fontWeight = FontWeight.Bold, fontSize = 13.sp, color = Color(0xFF15803D), modifier = Modifier.padding(top = 6.dp))
                                }
                            )
                            step(
                                content = { Text("💎", fontSize = 16.sp) },
                                trailingLabel = {
                                    Text("Premium", fontWeight = FontWeight.Medium, fontSize = 13.sp, color = Color(0xFF166534), modifier = Modifier.padding(top = 6.dp))
                                }
                            )
                        }
                    }
                }
            }
        }

        // 12. Horizontal Cut-Corner Geometric Shape (Cyberpunk / Gold Styling)
        renderToFile(760, 160, File(outDir, "horizontal_cut_corner.png")) {
            MaterialTheme(colorScheme = darkColorScheme()) {
                Surface(
                    color = Color(0xFF090D16),
                    modifier = Modifier.size(760.dp, 160.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp)
                            .background(Color(0xFF0F172A), RoundedCornerShape(16.dp))
                            .border(1.dp, Color(0xFF1E293B), RoundedCornerShape(16.dp))
                            .padding(horizontal = 40.dp, vertical = 20.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        KotStep(
                            currentStep = { 1.5f },
                            style = KotStepStyle(
                                stepLayoutStyle = StepLayoutStyle.Horizontal,
                                stepStyle = StepStyles.default().copy(
                                    onTodo = StepStyle(
                                        stepSize = 36.dp,
                                        stepShape = CutCornerShape(8.dp),
                                        stepColor = Color(0xFF1E293B),
                                        textStyle = androidx.compose.ui.text.TextStyle(color = Color(0xFF64748B), fontWeight = FontWeight.Bold, fontSize = 13.sp),
                                        borderStyle = BorderStyle(width = 1.dp, color = Color(0xFF334155), shape = CutCornerShape(8.dp))
                                    ),
                                    onCurrent = StepStyle(
                                        stepSize = 42.dp,
                                        stepShape = CutCornerShape(8.dp),
                                        stepColor = Color(0xFFD97706),
                                        textStyle = androidx.compose.ui.text.TextStyle(color = Color.Black, fontWeight = FontWeight.Bold, fontSize = 14.sp),
                                        borderStyle = BorderStyle(width = 3.dp, color = Color(0xFFFDE68A), shape = CutCornerShape(8.dp))
                                    ),
                                    onDone = StepStyle(
                                        stepSize = 36.dp,
                                        stepShape = CutCornerShape(8.dp),
                                        stepColor = Color(0xFFF59E0B),
                                        textStyle = androidx.compose.ui.text.TextStyle(color = Color.Black, fontWeight = FontWeight.Bold, fontSize = 13.sp),
                                        borderStyle = BorderStyle(width = 1.dp, color = Color(0xFFFBBF24), shape = CutCornerShape(8.dp))
                                    )
                                ),
                                lineStyle = LineStyles.default().copy(
                                    onTodo = LineStyle(lineLength = 130.dp, lineThickness = 3.dp, lineColor = Color(0xFF334155)),
                                    onCurrent = LineStyle(lineLength = 130.dp, lineThickness = 3.dp, lineColor = Color(0xFF334155), progressColor = Color(0xFFF59E0B)),
                                    onDone = LineStyle(lineLength = 130.dp, lineThickness = 3.dp, lineColor = Color(0xFFF59E0B), progressColor = Color(0xFFF59E0B))
                                )
                            )
                        ) {
                            step(
                                title = "1",
                                trailingLabel = {
                                    Text("Phase Alpha", fontWeight = FontWeight.Medium, fontSize = 12.sp, color = Color(0xFFE2E8F0), modifier = Modifier.padding(top = 6.dp))
                                }
                            )
                            step(
                                title = "2",
                                trailingLabel = {
                                    Text("Phase Beta", fontWeight = FontWeight.Bold, fontSize = 12.sp, color = Color(0xFFFBBF24), modifier = Modifier.padding(top = 6.dp))
                                }
                            )
                            step(
                                title = "3",
                                trailingLabel = {
                                    Text("Phase Gamma", fontWeight = FontWeight.Medium, fontSize = 12.sp, color = Color(0xFF94A3B8), modifier = Modifier.padding(top = 6.dp))
                                }
                            )
                            step(
                                title = "4",
                                trailingLabel = {
                                    Text("Launch Final", fontWeight = FontWeight.Medium, fontSize = 12.sp, color = Color(0xFF64748B), modifier = Modifier.padding(top = 6.dp))
                                }
                            )
                        }
                    }
                }
            }
        }

        // 13. Vertical Minimal Rail (No leading timestamps, clean compact activity rail)
        renderToFile(420, 320, File(outDir, "vertical_minimal_rail.png")) {
            MaterialTheme(colorScheme = lightColorScheme()) {
                Surface(
                    color = Color.White,
                    modifier = Modifier.size(420.dp, 320.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp)
                            .background(Color(0xFFF8FAFC), RoundedCornerShape(16.dp))
                            .border(1.dp, Color(0xFFE2E8F0), RoundedCornerShape(16.dp))
                            .padding(24.dp)
                    ) {
                        KotStep(
                            currentStep = { 2f },
                            style = KotStepStyle(
                                stepLayoutStyle = StepLayoutStyle.Vertical,
                                stepStyle = StepStyles.default().copy(
                                    onTodo = StepStyle(
                                        stepSize = 26.dp,
                                        stepColor = Color(0xFFCBD5E1),
                                        textStyle = androidx.compose.ui.text.TextStyle(color = Color.White, fontWeight = FontWeight.Bold, fontSize = 11.sp)
                                    ),
                                    onCurrent = StepStyle(
                                        stepSize = 30.dp,
                                        stepColor = Color(0xFF3B82F6),
                                        textStyle = androidx.compose.ui.text.TextStyle(color = Color.White, fontWeight = FontWeight.Bold, fontSize = 12.sp),
                                        borderStyle = BorderStyle(width = 3.dp, color = Color(0xFFBFDBFE))
                                    ),
                                    onDone = StepStyle(
                                        stepSize = 26.dp,
                                        stepColor = Color(0xFF10B981),
                                        textStyle = androidx.compose.ui.text.TextStyle(color = Color.White, fontWeight = FontWeight.Bold, fontSize = 11.sp)
                                    )
                                ),
                                lineStyle = LineStyles.default().copy(
                                    onTodo = LineStyle(lineLength = 32.dp, lineThickness = 3.dp, lineColor = Color(0xFFE2E8F0), lineStrokeCap = StrokeCap.Round),
                                    onCurrent = LineStyle(lineLength = 32.dp, lineThickness = 3.dp, lineColor = Color(0xFFE2E8F0), progressColor = Color(0xFF3B82F6), lineStrokeCap = StrokeCap.Round),
                                    onDone = LineStyle(lineLength = 32.dp, lineThickness = 3.dp, lineColor = Color(0xFF10B981), progressColor = Color(0xFF10B981), lineStrokeCap = StrokeCap.Round)
                                )
                            )
                        ) {
                            step(
                                title = "1",
                                trailingLabel = {
                                    Column(modifier = Modifier.padding(start = 12.dp)) {
                                        Text("Database Schema Migration", fontWeight = FontWeight.Bold, fontSize = 13.sp, color = Color(0xFF0F172A))
                                        Text("All PostgreSQL tables updated", fontSize = 11.sp, color = Color(0xFF64748B))
                                    }
                                }
                            )
                            step(
                                title = "2",
                                trailingLabel = {
                                    Column(modifier = Modifier.padding(start = 12.dp)) {
                                        Text("OAuth 2.0 Integration", fontWeight = FontWeight.Bold, fontSize = 13.sp, color = Color(0xFF0F172A))
                                        Text("Google & Apple login configured", fontSize = 11.sp, color = Color(0xFF64748B))
                                    }
                                }
                            )
                            step(
                                title = "3",
                                trailingLabel = {
                                    Column(modifier = Modifier.padding(start = 12.dp)) {
                                        Text("GraphQL Gateway Routes", fontWeight = FontWeight.Bold, fontSize = 13.sp, color = Color(0xFF3B82F6))
                                        Text("Validating federation endpoints", fontSize = 11.sp, color = Color(0xFF64748B))
                                    }
                                }
                            )
                            step(
                                title = "4",
                                trailingLabel = {
                                    Column(modifier = Modifier.padding(start = 12.dp)) {
                                        Text("Production Rollout", fontWeight = FontWeight.SemiBold, fontSize = 13.sp, color = Color(0xFF94A3B8))
                                        Text("Traffic canary test pending", fontSize = 11.sp, color = Color(0xFF94A3B8))
                                    }
                                }
                            )
                        }
                    }
                }
            }
        }

        // 14. Vertical Dashed Flight Transit Route
        renderToFile(560, 360, File(outDir, "vertical_dashed_route.png")) {
            MaterialTheme(colorScheme = lightColorScheme()) {
                Surface(
                    color = Color.White,
                    modifier = Modifier.size(560.dp, 360.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp)
                            .background(Color(0xFFF8FAFC), RoundedCornerShape(16.dp))
                            .border(1.dp, Color(0xFFE2E8F0), RoundedCornerShape(16.dp))
                            .padding(24.dp)
                    ) {
                        KotStep(
                            currentStep = { 2f },
                            style = KotStepStyle(
                                stepLayoutStyle = StepLayoutStyle.Vertical,
                                stepStyle = StepStyles.default().copy(
                                    onTodo = StepStyle(
                                        stepSize = 28.dp,
                                        stepColor = Color(0xFFE2E8F0),
                                        textStyle = androidx.compose.ui.text.TextStyle(color = Color(0xFF64748B), fontWeight = FontWeight.Bold, fontSize = 12.sp)
                                    ),
                                    onCurrent = StepStyle(
                                        stepSize = 32.dp,
                                        stepColor = Color(0xFF0284C7),
                                        textStyle = androidx.compose.ui.text.TextStyle(color = Color.White, fontWeight = FontWeight.Bold, fontSize = 13.sp),
                                        borderStyle = BorderStyle(width = 3.dp, color = Color(0xFFBAE6FD))
                                    ),
                                    onDone = StepStyle(
                                        stepSize = 28.dp,
                                        stepColor = Color(0xFF0369A1),
                                        textStyle = androidx.compose.ui.text.TextStyle(color = Color.White, fontWeight = FontWeight.Bold, fontSize = 12.sp)
                                    )
                                ),
                                lineStyle = LineStyles.default().copy(
                                    onTodo = LineStyle(
                                        lineLength = 32.dp,
                                        lineThickness = 3.dp,
                                        lineColor = Color(0xFFCBD5E1),
                                        lineType = LineType.Dashed(dashLength = 6.dp, gapLength = 6.dp)
                                    ),
                                    onCurrent = LineStyle(
                                        lineLength = 32.dp,
                                        lineThickness = 3.dp,
                                        lineColor = Color(0xFFCBD5E1),
                                        progressColor = Color(0xFF0284C7),
                                        lineType = LineType.Dashed(dashLength = 6.dp, gapLength = 6.dp)
                                    ),
                                    onDone = LineStyle(
                                        lineLength = 32.dp,
                                        lineThickness = 3.dp,
                                        lineColor = Color(0xFF0369A1),
                                        progressColor = Color(0xFF0369A1),
                                        lineType = LineType.Dashed(dashLength = 6.dp, gapLength = 6.dp)
                                    )
                                )
                            )
                        ) {
                            step(
                                title = "1",
                                leadingLabel = {
                                    Text("08:00 AM", fontSize = 12.sp, fontWeight = FontWeight.Medium, color = Color(0xFF64748B), modifier = Modifier.padding(end = 16.dp, top = 4.dp))
                                },
                                trailingLabel = {
                                    Column(modifier = Modifier.padding(start = 14.dp)) {
                                        Text("San Francisco (SFO)", fontWeight = FontWeight.Bold, fontSize = 14.sp, color = Color(0xFF0F172A))
                                        Text("Flight UA 402 - Departed Terminal 3", fontSize = 12.sp, color = Color(0xFF64748B))
                                    }
                                }
                            )
                            step(
                                title = "2",
                                leadingLabel = {
                                    Text("01:15 PM", fontSize = 12.sp, fontWeight = FontWeight.Medium, color = Color(0xFF64748B), modifier = Modifier.padding(end = 16.dp, top = 4.dp))
                                },
                                trailingLabel = {
                                    Column(modifier = Modifier.padding(start = 14.dp)) {
                                        Text("Denver (DEN)", fontWeight = FontWeight.Bold, fontSize = 14.sp, color = Color(0xFF0F172A))
                                        Text("Landed Gate B22 - Transfer connection", fontSize = 12.sp, color = Color(0xFF64748B))
                                    }
                                }
                            )
                            step(
                                title = "3",
                                leadingLabel = {
                                    Text("05:40 PM", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = Color(0xFF0284C7), modifier = Modifier.padding(end = 16.dp, top = 4.dp))
                                },
                                trailingLabel = {
                                    Column(modifier = Modifier.padding(start = 14.dp)) {
                                        Text("Chicago (ORD)", fontWeight = FontWeight.Bold, fontSize = 14.sp, color = Color(0xFF0284C7))
                                        Text("Boarding Flight UA 890 - Gate C10", fontSize = 12.sp, color = Color(0xFF64748B))
                                    }
                                }
                            )
                            step(
                                title = "4",
                                leadingLabel = {
                                    Text("09:30 PM", fontSize = 12.sp, fontWeight = FontWeight.Medium, color = Color(0xFF94A3B8), modifier = Modifier.padding(end = 16.dp, top = 4.dp))
                                },
                                trailingLabel = {
                                    Column(modifier = Modifier.padding(start = 14.dp)) {
                                        Text("New York (JFK)", fontWeight = FontWeight.SemiBold, fontSize = 14.sp, color = Color(0xFF94A3B8))
                                        Text("Estimated arrival Terminal 7", fontSize = 12.sp, color = Color(0xFF94A3B8))
                                    }
                                }
                            )
                        }
                    }
                }
            }
        }

        // 15. Vertical Dark Theme CI/CD Pipeline
        renderToFile(580, 360, File(outDir, "vertical_dark_pipeline.png")) {
            MaterialTheme(colorScheme = darkColorScheme()) {
                Surface(
                    color = Color(0xFF090D16),
                    modifier = Modifier.size(580.dp, 360.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp)
                            .background(Color(0xFF0F172A), RoundedCornerShape(16.dp))
                            .border(1.dp, Color(0xFF1E293B), RoundedCornerShape(16.dp))
                            .padding(24.dp)
                    ) {
                        KotStep(
                            currentStep = { 2f },
                            style = KotStepStyle(
                                stepLayoutStyle = StepLayoutStyle.Vertical,
                                stepStyle = StepStyles.default().copy(
                                    onTodo = StepStyle(
                                        stepSize = 28.dp,
                                        stepColor = Color(0xFF1E293B),
                                        textStyle = androidx.compose.ui.text.TextStyle(color = Color(0xFF64748B), fontWeight = FontWeight.Bold, fontSize = 12.sp),
                                        borderStyle = BorderStyle(width = 1.dp, color = Color(0xFF334155))
                                    ),
                                    onCurrent = StepStyle(
                                        stepSize = 32.dp,
                                        stepColor = Color(0xFF06B6D4),
                                        textStyle = androidx.compose.ui.text.TextStyle(color = Color.Black, fontWeight = FontWeight.Bold, fontSize = 13.sp),
                                        borderStyle = BorderStyle(width = 3.dp, color = Color(0xFF67E8F9))
                                    ),
                                    onDone = StepStyle(
                                        stepSize = 28.dp,
                                        stepColor = Color(0xFF10B981),
                                        textStyle = androidx.compose.ui.text.TextStyle(color = Color.White, fontWeight = FontWeight.Bold, fontSize = 12.sp)
                                    )
                                ),
                                lineStyle = LineStyles.default().copy(
                                    onTodo = LineStyle(lineLength = 32.dp, lineThickness = 3.dp, lineColor = Color(0xFF1E293B), lineStrokeCap = StrokeCap.Round),
                                    onCurrent = LineStyle(lineLength = 32.dp, lineThickness = 3.dp, lineColor = Color(0xFF1E293B), progressColor = Color(0xFF06B6D4), lineStrokeCap = StrokeCap.Round),
                                    onDone = LineStyle(lineLength = 32.dp, lineThickness = 3.dp, lineColor = Color(0xFF10B981), progressColor = Color(0xFF10B981), lineStrokeCap = StrokeCap.Round)
                                )
                            )
                        ) {
                            step(
                                title = "1",
                                leadingLabel = {
                                    Text("14s", fontSize = 12.sp, fontWeight = FontWeight.SemiBold, color = Color(0xFF34D399), modifier = Modifier.padding(end = 16.dp, top = 4.dp))
                                },
                                trailingLabel = {
                                    Column(modifier = Modifier.padding(start = 14.dp)) {
                                        Text("Static Code Analysis & Lint", fontWeight = FontWeight.Bold, fontSize = 14.sp, color = Color(0xFFF1F5F9))
                                        Text("ktlint & detekt passed with 0 warnings", fontSize = 12.sp, color = Color(0xFF94A3B8))
                                    }
                                }
                            )
                            step(
                                title = "2",
                                leadingLabel = {
                                    Text("52s", fontSize = 12.sp, fontWeight = FontWeight.SemiBold, color = Color(0xFF34D399), modifier = Modifier.padding(end = 16.dp, top = 4.dp))
                                },
                                trailingLabel = {
                                    Column(modifier = Modifier.padding(start = 14.dp)) {
                                        Text("Multiplatform Test Suites", fontWeight = FontWeight.Bold, fontSize = 14.sp, color = Color(0xFFF1F5F9))
                                        Text("Android, Desktop & iOS simulator tests passed", fontSize = 12.sp, color = Color(0xFF94A3B8))
                                    }
                                }
                            )
                            step(
                                title = "3",
                                leadingLabel = {
                                    Text("Running", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = Color(0xFF22D3EE), modifier = Modifier.padding(end = 16.dp, top = 4.dp))
                                },
                                trailingLabel = {
                                    Column(modifier = Modifier.padding(start = 14.dp)) {
                                        Text("Docker Container Build", fontWeight = FontWeight.Bold, fontSize = 14.sp, color = Color(0xFF22D3EE))
                                        Text("Building OCI multi-arch image layer 4/7", fontSize = 12.sp, color = Color(0xFF94A3B8))
                                    }
                                }
                            )
                            step(
                                title = "4",
                                leadingLabel = {
                                    Text("Queued", fontSize = 12.sp, fontWeight = FontWeight.Medium, color = Color(0xFF64748B), modifier = Modifier.padding(end = 16.dp, top = 4.dp))
                                },
                                trailingLabel = {
                                    Column(modifier = Modifier.padding(start = 14.dp)) {
                                        Text("Deploy to Production", fontWeight = FontWeight.SemiBold, fontSize = 14.sp, color = Color(0xFF64748B))
                                        Text("Canary deployment to cluster us-central1", fontSize = 12.sp, color = Color(0xFF64748B))
                                    }
                                }
                            )
                        }
                    }
                }
            }
        }

        // 16. Vertical Roadmap Milestones with Custom Icons
        renderToFile(560, 360, File(outDir, "vertical_milestone_icons.png")) {
            MaterialTheme(colorScheme = lightColorScheme()) {
                Surface(
                    color = Color.White,
                    modifier = Modifier.size(560.dp, 360.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp)
                            .background(Color(0xFFF8FAFC), RoundedCornerShape(16.dp))
                            .border(1.dp, Color(0xFFE2E8F0), RoundedCornerShape(16.dp))
                            .padding(24.dp)
                    ) {
                        KotStep(
                            currentStep = { 2f },
                            style = KotStepStyle(
                                stepLayoutStyle = StepLayoutStyle.Vertical,
                                stepStyle = StepStyles.default().copy(
                                    onTodo = StepStyle(
                                        stepSize = 32.dp,
                                        stepColor = Color(0xFFE2E8F0),
                                        iconStyle = IconStyle(iconTint = Color(0xFF94A3B8), iconSize = 16.dp)
                                    ),
                                    onCurrent = StepStyle(
                                        stepSize = 36.dp,
                                        stepColor = Color(0xFF8B5CF6),
                                        iconStyle = IconStyle(iconTint = Color.White, iconSize = 18.dp),
                                        borderStyle = BorderStyle(width = 3.dp, color = Color(0xFFDDD6FE))
                                    ),
                                    onDone = StepStyle(
                                        stepSize = 32.dp,
                                        stepColor = Color(0xFF7C3AED),
                                        iconStyle = IconStyle(iconTint = Color.White, iconSize = 16.dp)
                                    )
                                ),
                                lineStyle = LineStyles.default().copy(
                                    onTodo = LineStyle(lineLength = 32.dp, lineThickness = 3.dp, lineColor = Color(0xFFE2E8F0), lineStrokeCap = StrokeCap.Round),
                                    onCurrent = LineStyle(lineLength = 32.dp, lineThickness = 3.dp, lineColor = Color(0xFFE2E8F0), progressColor = Color(0xFF8B5CF6), lineStrokeCap = StrokeCap.Round),
                                    onDone = LineStyle(lineLength = 32.dp, lineThickness = 3.dp, lineColor = Color(0xFF7C3AED), progressColor = Color(0xFF7C3AED), lineStrokeCap = StrokeCap.Round)
                                )
                            )
                        ) {
                            step(
                                icon = Icons.Default.Home,
                                leadingLabel = {
                                    Text("Q1 2026", fontSize = 12.sp, fontWeight = FontWeight.Medium, color = Color(0xFF64748B), modifier = Modifier.padding(end = 16.dp, top = 6.dp))
                                },
                                trailingLabel = {
                                    Column(modifier = Modifier.padding(start = 14.dp)) {
                                        Text("Foundation & Core Architecture", fontWeight = FontWeight.Bold, fontSize = 14.sp, color = Color(0xFF0F172A))
                                        Text("Multiplatform Gradle setup & initial DSL v3", fontSize = 12.sp, color = Color(0xFF64748B))
                                    }
                                }
                            )
                            step(
                                icon = Icons.Default.Person,
                                leadingLabel = {
                                    Text("Q2 2026", fontSize = 12.sp, fontWeight = FontWeight.Medium, color = Color(0xFF64748B), modifier = Modifier.padding(end = 16.dp, top = 6.dp))
                                },
                                trailingLabel = {
                                    Column(modifier = Modifier.padding(start = 14.dp)) {
                                        Text("Compose Multiplatform Targets", fontWeight = FontWeight.Bold, fontSize = 14.sp, color = Color(0xFF0F172A))
                                        Text("Desktop (Skiko), iOS and Wasm engine support", fontSize = 12.sp, color = Color(0xFF64748B))
                                    }
                                }
                            )
                            step(
                                icon = Icons.Default.Star,
                                leadingLabel = {
                                    Text("Q3 2026", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = Color(0xFF8B5CF6), modifier = Modifier.padding(end = 16.dp, top = 6.dp))
                                },
                                trailingLabel = {
                                    Column(modifier = Modifier.padding(start = 14.dp)) {
                                        Text("Public V3 Release & Showcase", fontWeight = FontWeight.Bold, fontSize = 14.sp, color = Color(0xFF8B5CF6))
                                        Text("Comprehensive documentation and interactive demo", fontSize = 12.sp, color = Color(0xFF64748B))
                                    }
                                }
                            )
                            step(
                                icon = Icons.Default.Favorite,
                                leadingLabel = {
                                    Text("Q4 2026", fontSize = 12.sp, fontWeight = FontWeight.Medium, color = Color(0xFF94A3B8), modifier = Modifier.padding(end = 16.dp, top = 6.dp))
                                },
                                trailingLabel = {
                                    Column(modifier = Modifier.padding(start = 14.dp)) {
                                        Text("Ecosystem & Theme Presets", fontWeight = FontWeight.SemiBold, fontSize = 14.sp, color = Color(0xFF94A3B8))
                                        Text("Pre-packaged theme marketplace & extensions", fontSize = 12.sp, color = Color(0xFF94A3B8))
                                    }
                                }
                            )
                        }
                    }
                }
            }
        }

        // 17. Vertical Mixed Slot Permutations (Both, Leading only, Trailing only, None)
        renderToFile(580, 360, File(outDir, "vertical_mixed_slots.png")) {
            MaterialTheme(colorScheme = lightColorScheme()) {
                Surface(
                    color = Color.White,
                    modifier = Modifier.size(580.dp, 360.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp)
                            .background(Color(0xFFF8FAFC), RoundedCornerShape(16.dp))
                            .border(1.dp, Color(0xFFE2E8F0), RoundedCornerShape(16.dp))
                            .padding(24.dp)
                    ) {
                        KotStep(
                            currentStep = { 2f },
                            style = KotStepStyle(
                                stepLayoutStyle = StepLayoutStyle.Vertical,
                                stepStyle = StepStyles.default().copy(
                                    onTodo = StepStyle(
                                        stepSize = 28.dp,
                                        stepColor = Color(0xFFCBD5E1),
                                        textStyle = androidx.compose.ui.text.TextStyle(color = Color.White, fontWeight = FontWeight.Bold, fontSize = 12.sp)
                                    ),
                                    onCurrent = StepStyle(
                                        stepSize = 32.dp,
                                        stepColor = Color(0xFF6366F1),
                                        textStyle = androidx.compose.ui.text.TextStyle(color = Color.White, fontWeight = FontWeight.Bold, fontSize = 13.sp),
                                        borderStyle = BorderStyle(width = 3.dp, color = Color(0xFFC7D2FE))
                                    ),
                                    onDone = StepStyle(
                                        stepSize = 28.dp,
                                        stepColor = Color(0xFF10B981),
                                        textStyle = androidx.compose.ui.text.TextStyle(color = Color.White, fontWeight = FontWeight.Bold, fontSize = 12.sp)
                                    )
                                ),
                                lineStyle = LineStyles.default().copy(
                                    onTodo = LineStyle(lineLength = 32.dp, lineThickness = 3.dp, lineColor = Color(0xFFE2E8F0), lineStrokeCap = StrokeCap.Round),
                                    onCurrent = LineStyle(lineLength = 32.dp, lineThickness = 3.dp, lineColor = Color(0xFFE2E8F0), progressColor = Color(0xFF6366F1), lineStrokeCap = StrokeCap.Round),
                                    onDone = LineStyle(lineLength = 32.dp, lineThickness = 3.dp, lineColor = Color(0xFF10B981), progressColor = Color(0xFF10B981), lineStrokeCap = StrokeCap.Round)
                                )
                            )
                        ) {
                            // Step 1: BOTH leading and trailing
                            step(
                                title = "1",
                                leadingLabel = {
                                    Text("09:00 AM", fontSize = 12.sp, fontWeight = FontWeight.Medium, color = Color(0xFF64748B), modifier = Modifier.padding(end = 16.dp, top = 4.dp))
                                },
                                trailingLabel = {
                                    Column(modifier = Modifier.padding(start = 14.dp)) {
                                        Text("Order Placed", fontWeight = FontWeight.Bold, fontSize = 14.sp, color = Color(0xFF0F172A))
                                        Text("Both leading and trailing labels", fontSize = 12.sp, color = Color(0xFF64748B))
                                    }
                                }
                            )

                            // Step 2: JUST leading label
                            step(
                                title = "2",
                                leadingLabel = {
                                    Text("11:30 AM", fontSize = 12.sp, fontWeight = FontWeight.Medium, color = Color(0xFF64748B), modifier = Modifier.padding(end = 16.dp, top = 4.dp))
                                }
                            )

                            // Step 3: JUST trailing label
                            step(
                                title = "3",
                                trailingLabel = {
                                    Column(modifier = Modifier.padding(start = 14.dp)) {
                                        Text("Out for Delivery", fontWeight = FontWeight.Bold, fontSize = 14.sp, color = Color(0xFF6366F1))
                                        Text("Trailing label only (no leading label)", fontSize = 12.sp, color = Color(0xFF64748B))
                                    }
                                }
                            )

                            // Step 4: NONE (neither leading nor trailing)
                            step(
                                title = "4"
                            )
                        }
                    }
                }
            }
        }
    }

    @Composable
    private fun PlatformBadge(name: String) {
        Box(
            modifier = Modifier
                .background(Color(0xFFF1F5F9), RoundedCornerShape(6.dp))
                .border(1.dp, Color(0xFFE2E8F0), RoundedCornerShape(6.dp))
                .padding(horizontal = 8.dp, vertical = 4.dp)
        ) {
            Text(name, fontSize = 11.sp, fontWeight = FontWeight.SemiBold, color = Color(0xFF475569))
        }
    }

    @Composable
    private fun FeaturePill(title: String) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(title, fontSize = 12.sp, fontWeight = FontWeight.Medium, color = Color(0xFF475569))
        }
    }
}
