package com.example.settings.presentation.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.settings.presentation.PinManager

@Composable
fun SetPinScreen(
    pinManager: PinManager,
    screenState: MutableState<Boolean>
) {
    var currentStep by remember { mutableStateOf(StepPin.FIRSt) }
    var firstPin by remember { mutableStateOf("") }
    var currentPin by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Start
        ) {
            IconButton(onClick = {
                screenState.value = false
            }) {
                Icon(Icons.Default.Close, contentDescription = "")
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = when (currentStep) {
                StepPin.FIRSt -> "Установите PIN"
                StepPin.SECOND -> "Подтвердите PIN"
            },
            color = MaterialTheme.colorScheme.onBackground
        )
        OutlinedTextField(
            value = currentPin,
            onValueChange = { newValue ->
                if (newValue.length <= 4 && newValue.all { it.isDigit() }) {
                    currentPin = newValue
                    if (newValue.length == 4) {
                        when (currentStep) {
                            StepPin.FIRSt -> {
                                firstPin = newValue
                                currentStep = StepPin.SECOND
                                currentPin = ""
                            }

                            StepPin.SECOND -> {
                                if (newValue == firstPin) {
                                    pinManager.setNewPin(newValue)
                                    screenState.value = false
                                } else {
                                    errorMessage = "PIN-коды не совпадают"
                                    currentStep = StepPin.FIRSt
                                    currentPin = ""
                                    firstPin = ""
                                }
                            }
                        }
                    }
                }
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            visualTransformation = PasswordVisualTransformation(),
            textStyle = LocalTextStyle.current.copy(
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onBackground
            ),
            singleLine = true,
            modifier = Modifier.width(150.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))
        if (errorMessage != null) {
            Text(errorMessage!!, color = MaterialTheme.colorScheme.error)
        }
        Spacer(modifier = Modifier.height(32.dp))
        Button(
            onClick = {
                pinManager.clearPin()
                screenState.value = false
            }
        ) {
            Text("Сбросить код", color = MaterialTheme.colorScheme.onPrimary)
        }
    }

}

private enum class StepPin {
    FIRSt, SECOND
}