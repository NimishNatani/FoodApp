package com.foodapp.foodapp.presentation.components


import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.Placeable
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun TagPickerDropdown(
    allTags: List<String>,
    selected: List<String>,
    onTagToggled: (String) -> Unit,
    onClearAll: () -> Unit,
    onAdd: () -> Unit,
    placeholder: String = "Tags",
) {
    var expanded by remember { mutableStateOf(true) }

    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .shadow(8.dp, RoundedCornerShape(16.dp)),
        shape = RoundedCornerShape(16.dp),
        color = Color.White,
        tonalElevation = 4.dp
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            // Header row with toggle
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { expanded = !expanded }
            ) {
                Text(
                    text = placeholder,
                    style = MaterialTheme.typography.titleMedium,
                    color = Color.Black,
                    modifier = Modifier.weight(1f)
                )
                Icon(
                    imageVector = if (expanded) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                    contentDescription = if (expanded) "Collapse" else "Expand",
                    tint = Color.Gray
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            if (expanded) {
                // Selected tag pills (or hint)
                FlowRow(
                    mainAxisSpacing = 8.dp,
                    crossAxisSpacing = 8.dp,
                    modifier = Modifier.fillMaxWidth().animateContentSize()
                ) {
                    if (selected.isEmpty()) {
                        // placeholder when nothing selected
                        TagPill(
                            text = "No tags selected",
                            isSelected = false,
                            onRemove = {},
                            showRemove = false
                        )
                    } else {
                        selected.forEach { tag ->
                            TagPill(
                                text = tag,
                                isSelected = true,
                                onRemove = { onTagToggled(tag) }
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                // Optionally allow toggling from all tags (unselected ones)
                FlowRow(
                    mainAxisSpacing = 8.dp,
                    crossAxisSpacing = 8.dp,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    allTags.filter { it !in selected }.forEach { tag ->
                        TagPill(
                            text = tag,
                            isSelected = false,
                            onRemove = { onTagToggled(tag) }
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Action row
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    TextButton(onClick = onClearAll) {
                        Text("Clear All")
                    }
                }
            }
        }
    }
}

@Composable
private fun TagPill(
    text: String,
    isSelected: Boolean,
    onRemove: () -> Unit,
    cornerRadius: Dp = 50.dp,
    showRemove: Boolean = true
) {
    val backgroundColor by animateColorAsState(
        targetValue = if (isSelected) Color(0xFFE3EBFF) else Color(0xFFF5F5F5)
    )
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .background(backgroundColor, RoundedCornerShape(cornerRadius))
            .border(
                width = 1.dp,
                color = Color.Gray.copy(alpha = 0.5f),
                shape = RoundedCornerShape(cornerRadius)
            )
            .clickable { if (!isSelected) onRemove() } // toggle on unselected tap
            .padding(horizontal = 12.dp, vertical = 6.dp)
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.bodyMedium,
            color = Color.Black
        )
        if (showRemove && isSelected) {
            Spacer(modifier = Modifier.width(6.dp))
            Box(
                modifier = Modifier
                    .size(18.dp)
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null
                    ) { onRemove() },
                contentAlignment = Alignment.Center
            ) {
                Text("×", style = MaterialTheme.typography.bodyMedium, color = Color.DarkGray)
            }
        }
    }
}


// Simple fallback if you don't want a dependency:
@Composable
fun FlowRow(
    modifier: Modifier = Modifier,
    mainAxisSpacing: Dp = 0.dp,
    crossAxisSpacing: Dp = 0.dp,
    content: @Composable () -> Unit
) {
    Layout(
        content = content,
        modifier = modifier
    ) { measurables, constraints ->
        val maxWidth = constraints.maxWidth.takeIf { it != Constraints.Infinity } ?: constraints.maxWidth

        val rowHeights = mutableListOf<Int>()
        val placeablesInRows = mutableListOf<MutableList<Placeable>>()

        var currentRow = mutableListOf<Placeable>()
        var currentRowWidth = 0
        var currentRowHeight = 0

        val horizontalSpacingPx = mainAxisSpacing.roundToPx()
        val verticalSpacingPx = crossAxisSpacing.roundToPx()

        for (measurable in measurables) {
            // measure with loosened width so big items can still get measured
            val placeable = measurable.measure(constraints.copy(minWidth = 0))
            val placeableWidth = placeable.width
            val placeableHeight = placeable.height

            val wouldExceed = if (currentRow.isEmpty()) {
                false
            } else {
                currentRowWidth + horizontalSpacingPx + placeableWidth > maxWidth
            }

            if (wouldExceed) {
                // wrap to next row
                rowHeights.add(currentRowHeight)
                placeablesInRows.add(currentRow)
                currentRow = mutableListOf()
                currentRowWidth = 0
                currentRowHeight = 0
            }

            if (currentRow.isNotEmpty()) {
                currentRowWidth += horizontalSpacingPx
            }
            currentRow.add(placeable)
            currentRowWidth += placeableWidth
            currentRowHeight = maxOf(currentRowHeight, placeableHeight)
        }

        if (currentRow.isNotEmpty()) {
            rowHeights.add(currentRowHeight)
            placeablesInRows.add(currentRow)
        }

        // total height: sum of row heights + spacing between rows (not after last)
        val totalHeight = if (rowHeights.isEmpty()) {
            0
        } else {
            rowHeights.sum() + verticalSpacingPx * (rowHeights.size - 1)
        }

        val layoutHeight = totalHeight.coerceIn(constraints.minHeight, constraints.maxHeight)
        layout(width = constraints.maxWidth, height = layoutHeight) {
            var yOffset = 0
            placeablesInRows.forEachIndexed { rowIndex, row ->
                var xOffset = 0
                val rowHeight = rowHeights[rowIndex]
                row.forEachIndexed { index, placeable ->
                    placeable.placeRelative(x = xOffset, y = yOffset)
                    xOffset += placeable.width
                    if (index != row.lastIndex) {
                        xOffset += horizontalSpacingPx
                    }
                }
                yOffset += rowHeight
                if (rowIndex != placeablesInRows.lastIndex) {
                    yOffset += verticalSpacingPx
                }
            }
        }
    }
}
