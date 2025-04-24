package com.github.ksalil.egghuntchecklist.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.github.ksalil.egghuntchecklist.presentation.ui.theme.EggFoundListRowColorGradient
import com.github.ksalil.egghuntchecklist.presentation.ui.theme.EggHuntChecklistTheme
import com.github.ksalil.egghuntchecklist.presentation.ui.theme.EggNotFoundListRowColorGradient

@Composable
fun EggHuntListRow(
    modifier: Modifier = Modifier,
    title:String,
    isChecked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    val backgroundColorGradient = if (isChecked) {
        EggFoundListRowColorGradient
    } else {
        EggNotFoundListRowColorGradient
    }
    Row(
        modifier = modifier
            .fillMaxWidth()
            //.padding(horizontal = 24.dp, vertical = 16.dp)
            .background(
                brush = Brush.verticalGradient(
                    colors = backgroundColorGradient
                ),
                shape = RoundedCornerShape(
                    size = 8.dp
                )
            ),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Checkbox(
            checked = isChecked,
            onCheckedChange = onCheckedChange,
            colors = CheckboxDefaults.colors(
                checkedColor = Color.White,
                uncheckedColor = Color.White,
                checkmarkColor = Color.Black
            )
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = title,
            color = Color.White,
            style = MaterialTheme.typography.titleMedium.copy(
                fontWeight = FontWeight.Bold
            )
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun EggHuntListRowPreview() {
    EggHuntChecklistTheme {
        EggHuntListRow(
            title = "Easter Egg",
            isChecked = false,
            onCheckedChange = {}
        )
    }
}