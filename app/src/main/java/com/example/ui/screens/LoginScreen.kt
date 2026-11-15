package com.example.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AddLink
import androidx.compose.material.icons.rounded.Key
import androidx.compose.material.icons.rounded.Lock
import androidx.compose.material.icons.rounded.Shield
import androidx.compose.material.icons.rounded.UploadFile
import androidx.compose.material.icons.rounded.VerifiedUser
import androidx.compose.material.icons.rounded.Visibility
import androidx.compose.material.icons.rounded.VisibilityOff
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.R
import com.example.ui.AppStrings
import com.example.ui.components.GlassOutlinedButton
import com.example.ui.components.GlassStatusBadge
import com.example.ui.components.LiquidGlassBackground
import com.example.ui.components.LiquidGlassCard
import com.example.ui.components.PrimaryGlowButton
import com.example.ui.theme.LocalGlassColors

@Composable
fun LoginScreen(
    strings: AppStrings,
    savedClientKey: String,
    savedClientSecret: String,
    isAuthLoading: Boolean,
    onContinueWithTikTok: (clientKey: String, clientSecret: String) -> Unit,
    onClearSavedCredentials: () -> Unit,
    onImportDataExport: () -> Unit,
    onPasteVideoLinks: () -> Unit,
    onContinueInspector: () -> Unit
) {
    val glass = LocalGlassColors.current
    var clientKey by remember(savedClientKey) { mutableStateOf(savedClientKey) }
    var clientSecret by remember(savedClientSecret) { mutableStateOf(savedClientSecret) }
    var showSecret by remember { mutableStateOf(false) }
    var validationError by remember { mutableStateOf<String?>(null) }

    LiquidGlassBackground {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .safeDrawingPadding()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 20.dp, vertical = 24.dp),
            contentAlignment = Alignment.Center
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .widthIn(max = 520.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(18.dp)
            ) {
                // Clean Minimal App Header
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_launcher_art_1794662452823),
                        contentDescription = "TikTok Cleaner Logo",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .size(68.dp)
                            .clip(RoundedCornerShape(20.dp))
                            .border(1.dp, glass.glassBorder, RoundedCornerShape(20.dp))
                    )
                    Spacer(modifier = Modifier.height(14.dp))
                    Text(
                        text = strings.appName,
                        style = MaterialTheme.typography.displaySmall,
                        color = glass.textPrimary,
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = strings.tagline,
                        style = MaterialTheme.typography.bodyMedium,
                        color = glass.textSecondary,
                        textAlign = TextAlign.Center
                    )
                }

                // Official TikTok Login Kit OAuth 2.0 Card with On-Device Encrypted Credentials
                LiquidGlassCard(
                    modifier = Modifier.fillMaxWidth(),
                    cornerRadius = 22.dp,
                    elevated = true,
                    contentPadding = PaddingValues(20.dp)
                ) {
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            GlassStatusBadge(
                                text = strings.officialApiBadge,
                                color = glass.accent,
                                icon = Icons.Rounded.VerifiedUser
                            )
                            GlassStatusBadge(
                                text = "OAuth 2.0",
                                color = glass.primary,
                                icon = Icons.Rounded.Lock
                            )
                        }

                        Text(
                            text = strings.loginOAuthSubtitle,
                            style = MaterialTheme.typography.bodyMedium,
                            color = glass.textSecondary
                        )

                        OutlinedTextField(
                            value = clientKey,
                            onValueChange = {
                                clientKey = it
                                validationError = null
                            },
                            label = { Text(strings.clientKeyLabel) },
                            placeholder = { Text(strings.clientKeyPlaceholder) },
                            leadingIcon = {
                                Icon(Icons.Rounded.Key, contentDescription = null)
                            },
                            singleLine = true,
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Ascii,
                                imeAction = ImeAction.Next
                            ),
                            shape = RoundedCornerShape(16.dp),
                            modifier = Modifier
                                .fillMaxWidth()
                                .testTag("client_key_input"),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = glass.primary,
                                unfocusedBorderColor = glass.glassBorder,
                                focusedTextColor = glass.textPrimary,
                                unfocusedTextColor = glass.textPrimary
                            )
                        )

                        OutlinedTextField(
                            value = clientSecret,
                            onValueChange = {
                                clientSecret = it
                                validationError = null
                            },
                            label = { Text(strings.clientSecretLabel) },
                            placeholder = { Text(strings.clientSecretPlaceholder) },
                            leadingIcon = {
                                Icon(Icons.Rounded.Lock, contentDescription = null)
                            },
                            trailingIcon = {
                                IconButton(
                                    onClick = { showSecret = !showSecret },
                                    modifier = Modifier.testTag("toggle_client_secret_visibility")
                                ) {
                                    Icon(
                                        imageVector = if (showSecret) Icons.Rounded.VisibilityOff else Icons.Rounded.Visibility,
                                        contentDescription = if (showSecret) "Hide client secret" else "Show client secret"
                                    )
                                }
                            },
                            singleLine = true,
                            visualTransformation = if (showSecret) VisualTransformation.None else PasswordVisualTransformation(),
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Password,
                                imeAction = ImeAction.Done
                            ),
                            shape = RoundedCornerShape(16.dp),
                            modifier = Modifier
                                .fillMaxWidth()
                                .testTag("client_secret_input"),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = glass.primary,
                                unfocusedBorderColor = glass.glassBorder,
                                focusedTextColor = glass.textPrimary,
                                unfocusedTextColor = glass.textPrimary
                            )
                        )

                        Text(
                            text = strings.clientCredentialsSecurityNote,
                            style = MaterialTheme.typography.bodySmall,
                            color = glass.textMuted
                        )

                        validationError?.let { err ->
                            Text(
                                text = err,
                                style = MaterialTheme.typography.bodySmall,
                                color = glass.danger,
                                modifier = Modifier.testTag("credentials_validation_error")
                            )
                        }

                        if (isAuthLoading) {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(52.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                CircularProgressIndicator(color = glass.primary)
                            }
                        } else {
                            PrimaryGlowButton(
                                text = strings.continueWithTikTok,
                                icon = Icons.Rounded.Shield,
                                onClick = {
                                    val trimmedKey = clientKey.trim()
                                    val trimmedSecret = clientSecret.trim()
                                    if (trimmedKey.isEmpty() || trimmedSecret.isEmpty()) {
                                        validationError = strings.clientCredentialsRequiredError
                                    } else {
                                        validationError = null
                                        onContinueWithTikTok(trimmedKey, trimmedSecret)
                                    }
                                },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .testTag("continue_with_tiktok_button")
                            )
                        }

                        if (savedClientKey.isNotBlank() || savedClientSecret.isNotBlank()) {
                            TextButton(
                                onClick = {
                                    clientKey = ""
                                    clientSecret = ""
                                    validationError = null
                                    onClearSavedCredentials()
                                },
                                modifier = Modifier
                                    .align(Alignment.End)
                                    .testTag("clear_credentials_button")
                            ) {
                                Text(
                                    text = strings.clearSavedCredentials,
                                    style = MaterialTheme.typography.labelMedium,
                                    color = glass.danger
                                )
                            }
                        }
                    }
                }

                // Simple Direct Import & Link Analysis Entry
                LiquidGlassCard(
                    modifier = Modifier.fillMaxWidth(),
                    cornerRadius = 22.dp,
                    contentPadding = PaddingValues(20.dp)
                ) {
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        verticalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Text(
                            text = strings.dataExportModeStatus,
                            style = MaterialTheme.typography.titleMedium,
                            color = glass.textPrimary
                        )
                        GlassOutlinedButton(
                            text = strings.importDataExport,
                            icon = Icons.Rounded.UploadFile,
                            onClick = onImportDataExport,
                            modifier = Modifier
                                .fillMaxWidth()
                                .testTag("login_import_json_button")
                        )
                        GlassOutlinedButton(
                            text = strings.pasteVideoLinks,
                            icon = Icons.Rounded.AddLink,
                            onClick = onPasteVideoLinks,
                            modifier = Modifier
                                .fillMaxWidth()
                                .testTag("login_paste_links_button")
                        )
                        GlassOutlinedButton(
                            text = strings.dashboard,
                            onClick = onContinueInspector,
                            tint = glass.primary,
                            modifier = Modifier
                                .fillMaxWidth()
                                .testTag("login_open_dashboard_button")
                        )
                    }
                }
            }
        }
    }
}
