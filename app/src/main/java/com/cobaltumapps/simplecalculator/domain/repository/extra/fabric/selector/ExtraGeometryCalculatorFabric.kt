package com.cobaltumapps.simplecalculator.domain.repository.extra.fabric.selector

import android.content.Context
import androidx.core.content.res.ResourcesCompat
import com.cobaltumapps.simplecalculator.R
import com.cobaltumapps.simplecalculator.data.extra.enums.ExtraCalculatorType
import com.cobaltumapps.simplecalculator.data.extra.selector.ExtraSelectableCalculatorInfo
import com.cobaltumapps.simplecalculator.domain.repository.extra.interfaces.ExtraSelectableCalculatorFabric
import com.cobaltumapps.simplecalculator.data.extra.constants.ExtraCalculatorsKeys as ECK

class ExtraGeometryCalculatorFabric: ExtraSelectableCalculatorFabric {
    override fun createCalculators(context: Context): List<ExtraSelectableCalculatorInfo> {
        val extraDrawables = with(context) {
            mapOf(
                ECK.CALC_SHAPES_POLYGON_ID to ResourcesCompat.getDrawable(resources, R.drawable.ic_polygon, theme),
                ECK.CALC_SHAPES_RIGHT_TRIANGLE_ID to ResourcesCompat.getDrawable(resources, R.drawable.ic_weight, theme),
                ECK.CALC_SHAPES_RECTANGLE_ID to ResourcesCompat.getDrawable(resources, R.drawable.ic_rectangle, theme),
                ECK.CALC_SHAPES_TRAPEZOID_ID to ResourcesCompat.getDrawable(resources, R.drawable.ic_weight, theme),
                ECK.CALC_SHAPES_RHOMBUS_ID to ResourcesCompat.getDrawable(resources, R.drawable.ic_rhombus, theme),
                ECK.CALC_SHAPES_CIRCLE_ID to ResourcesCompat.getDrawable(resources, R.drawable.ic_circle, theme),
                ECK.CALC_SHAPES_ELLIPSE_ID to ResourcesCompat.getDrawable(resources, R.drawable.ic_circle, theme),

                ECK.CALC_BODIES_PRISM_ID to ResourcesCompat.getDrawable(resources, R.drawable.ic_weight, theme),
                ECK.CALC_BODIES_PYRAMID_ID to ResourcesCompat.getDrawable(resources, R.drawable.ic_pyramid, theme),
                ECK.CALC_BODIES_PYRAMID_FRUSTUM_ID to ResourcesCompat.getDrawable(resources, R.drawable.ic_weight, theme),
                ECK.CALC_BODIES_CONE_ID to ResourcesCompat.getDrawable(resources, R.drawable.ic_weight, theme),
                ECK.CALC_BODIES_CONE_FRUSTUM_ID to ResourcesCompat.getDrawable(resources, R.drawable.ic_weight, theme),
                ECK.CALC_BODIES_CYLINDER_ID to ResourcesCompat.getDrawable(resources, R.drawable.ic_weight, theme),
                ECK.CALC_BODIES_SPHERE_ID to ResourcesCompat.getDrawable(resources, R.drawable.ic_weight, theme),
                ECK.CALC_BODIES_ELLIPSOID_ID to ResourcesCompat.getDrawable(resources, R.drawable.ic_weight, theme),
            )
        }

        val extraTitles = with(context) {
            mapOf(
                ECK.CALC_SHAPES_POLYGON_ID to getString(R.string.extra_calculator_polygon_name),
                ECK.CALC_SHAPES_RIGHT_TRIANGLE_ID to getString(R.string.extra_calculator_right_triangle_name),
                ECK.CALC_SHAPES_RECTANGLE_ID to getString(R.string.extra_calculator_rectangle_name),
                ECK.CALC_SHAPES_TRAPEZOID_ID to getString(R.string.extra_calculator_trapezoid_name),
                ECK.CALC_SHAPES_RHOMBUS_ID to getString(R.string.extra_calculator_rhombus_name),
                ECK.CALC_SHAPES_CIRCLE_ID to getString(R.string.extra_calculator_circle_name),
                ECK.CALC_SHAPES_ELLIPSE_ID to getString(R.string.extra_calculator_ellipse_name),
                ECK.CALC_BODIES_PRISM_ID to getString(R.string.extra_calculator_prism_name),
                ECK.CALC_BODIES_PYRAMID_ID to getString(R.string.extra_calculator_pyramid_name),
                ECK.CALC_BODIES_PYRAMID_FRUSTUM_ID to getString(R.string.extra_calculator_pyramidFrustum_name),
                ECK.CALC_BODIES_CONE_ID to getString(R.string.extra_calculator_cone_name),
                ECK.CALC_BODIES_CONE_FRUSTUM_ID to getString(R.string.extra_calculator_coneFrustum_name),
                ECK.CALC_BODIES_CYLINDER_ID to getString(R.string.extra_calculator_cylinder_name),
                ECK.CALC_BODIES_SPHERE_ID to getString(R.string.extra_calculator_sphere_name),
                ECK.CALC_BODIES_ELLIPSOID_ID to getString(R.string.extra_calculator_ellipsoid_name)
            )
        }

        val extraIds = listOf(
            ECK.CALC_SHAPES_POLYGON_ID,
            ECK.CALC_SHAPES_RIGHT_TRIANGLE_ID,
            ECK.CALC_SHAPES_RECTANGLE_ID,
            ECK.CALC_SHAPES_TRAPEZOID_ID,
            ECK.CALC_SHAPES_RHOMBUS_ID,
            ECK.CALC_SHAPES_CIRCLE_ID,
            ECK.CALC_SHAPES_ELLIPSE_ID,
            ECK.CALC_BODIES_PRISM_ID,
            ECK.CALC_BODIES_PYRAMID_ID,
            ECK.CALC_BODIES_PYRAMID_FRUSTUM_ID,
            ECK.CALC_BODIES_CONE_ID,
            ECK.CALC_BODIES_CONE_FRUSTUM_ID,
            ECK.CALC_BODIES_CYLINDER_ID,
            ECK.CALC_BODIES_SPHERE_ID,
            ECK.CALC_BODIES_ELLIPSOID_ID,
        )

        return extraIds.map { calcId ->
            ExtraSelectableCalculatorInfo(
                calculatorId = calcId,
                title = extraTitles[calcId] ?: "Unnamed",
                drawable = extraDrawables[calcId],
                previewValues = "",
                type = ExtraCalculatorType.Geometry
            )
        }
    }
}