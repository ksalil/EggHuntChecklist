package com.github.ksalil.egghuntchecklist.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.github.ksalil.egghuntchecklist.R
import com.github.ksalil.egghuntchecklist.data.eggLocationList
import com.github.ksalil.egghuntchecklist.data.getEasterFacts
import com.github.ksalil.egghuntchecklist.presentation.mvi.EggHuntAction
import com.github.ksalil.egghuntchecklist.presentation.mvi.EggHuntState
import com.github.ksalil.egghuntchecklist.presentation.ui.theme.Background
import com.github.ksalil.egghuntchecklist.presentation.ui.theme.ButtonBackgroundGradient
import com.github.ksalil.egghuntchecklist.presentation.ui.theme.EggHuntChecklistTheme
import com.github.ksalil.egghuntchecklist.presentation.ui.theme.Yellow
import com.github.ksalil.egghuntchecklist.presentation.ui.theme.titleGradient

@Composable
fun EggHuntScreen(
    modifier: Modifier = Modifier,
    viewModel: EggHuntViewModel
) {
    val state = viewModel.state.collectAsStateWithLifecycle()
    Scaffold(
        containerColor = Background
    ) { innerPadding ->
        EggHuntScreenContent(
            modifier = modifier
                .fillMaxSize()
                .padding(innerPadding),
            state = state.value,
            onEggTapped = {
                viewModel.onAction(EggHuntAction.OnEggTapped(it))
            },
            onDialogDismissed = {
                viewModel.onAction(EggHuntAction.OnDialogDismissed)
            }
        )
    }
}

@Composable
fun EggHuntScreenContent(
    modifier: Modifier = Modifier,
    state: EggHuntState,
    onEggTapped: (Int) -> Unit,
    onDialogDismissed: () -> Unit
) {
    LazyColumn(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item {
            Text(
                text = stringResource(R.string.screen_title),
                style = MaterialTheme.typography.headlineSmall.copy(
                    brush = Brush.verticalGradient(
                        colors = titleGradient
                    )
                )
            )
            Spacer(modifier = Modifier.height(32.dp))
            Text(
                modifier = Modifier
                    .padding(
                        horizontal = 32.dp,
                    ),
                text = stringResource(R.string.screen_subtitle),
                color = Color.White,
                style = MaterialTheme.typography.headlineSmall,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(8.dp))
            val eggsFoundText = "${state.foundEggs.size}/${state.eggList.size} eggs found"
            Text(
                text = eggsFoundText,
                color = Yellow,
                style = MaterialTheme.typography.titleMedium,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(24.dp))
        }

        // List of egg items
        items(
            items = state.eggList,
        ) { easterEgg ->
            EggHuntListRow(
                modifier = Modifier
                    .height(64.dp)
                    .padding(
                        vertical = 4.dp,
                        horizontal = 16.dp
                    ),
                title = easterEgg.title,
                isChecked = state.foundEggs.contains(easterEgg.id),
                onCheckedChange = { onEggTapped(easterEgg.id) }
            )
        }
    }
    if (state.currentEgg != null) {
        val isChecked = state.foundEggs.contains(state.currentEgg.id)
        ShowEasterDialog(
            isEggChecked = isChecked,
            onDismiss = onDialogDismissed
        )
    }
}

@Composable
fun ShowEasterDialog(
    isEggChecked: Boolean,
    onDismiss: () -> Unit
) {
    Dialog(
        onDismissRequest = onDismiss
    ) {
        Surface(
            modifier = Modifier
                .clip(RoundedCornerShape(16.dp)),
            color = Background
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                AnimatingProgressHeader(
                    isEggChecked = isEggChecked,
                    onProgressCompleted = onDismiss
                )
                Spacer(modifier = Modifier.height(8.dp))
                if (isEggChecked) {
                    Text(
                        text = stringResource(R.string.easter_fact),
                        style = MaterialTheme.typography.titleMedium,
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Image(
                        imageVector = ImageVector.vectorResource(id = R.drawable.ic_egg_checked),
                        contentDescription = ""
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    EasterFactText()
                } else {
                    Text(
                        text = stringResource(R.string.oops_the_egg_rolled_away),
                        style = MaterialTheme.typography.titleMedium,
                        color = Color.White
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Image(
                        imageVector = ImageVector.vectorResource(id = R.drawable.ic_egg_rolled_away),
                        contentDescription = ""
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                }
                Spacer(modifier = Modifier.height(24.dp))
                Button(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            brush = Brush.verticalGradient(
                                colors = ButtonBackgroundGradient
                            ),
                            shape = RoundedCornerShape(8.dp)
                        )
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Transparent
                    ),
                    contentPadding = PaddingValues(),
                    onClick = onDismiss
                ) {
                    Text(
                        text = stringResource(R.string.dismiss),
                        style = MaterialTheme.typography.titleMedium,
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}

@Composable
fun EasterFactText() {
    val easterFacts = getEasterFacts()
    val fact = stringResource(id = easterFacts.random().resId)
    Text(
        text = "\"" + fact + "\"",
        style = MaterialTheme.typography.titleMedium.copy(
            brush = Brush.verticalGradient(
                colors = titleGradient
            )
        ),
        textAlign = TextAlign.Center
    )
}

@Preview(showBackground = true)
@Composable
private fun ShowEasterDialogPreview() {
    EggHuntChecklistTheme {
        ShowEasterDialog(
            isEggChecked = true,
            onDismiss = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun EggHuntChecklistScreenContentPreview() {
    EggHuntChecklistTheme {
        EggHuntScreenContent(
            state = EggHuntState(
                eggList = eggLocationList,
                foundEggs = emptySet()
            ),
            onEggTapped = {},
            onDialogDismissed = {}
        )
    }
}