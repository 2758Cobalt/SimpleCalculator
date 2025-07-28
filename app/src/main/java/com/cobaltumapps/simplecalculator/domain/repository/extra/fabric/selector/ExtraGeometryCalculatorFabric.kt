package com.cobaltumapps.simplecalculator.domain.repository.extra.fabric.selector

import android.content.Context
import com.cobaltumapps.simplecalculator.R
import com.cobaltumapps.simplecalculator.data.extra.enums.ExtraCalculatorType
import com.cobaltumapps.simplecalculator.data.extra.selector.ExtraSelectableCalculatorInfo
import com.cobaltumapps.simplecalculator.domain.repository.extra.interfaces.ExtraSelectableCalculatorFabric
import com.cobaltumapps.simplecalculator.data.extra.constants.ExtraCalculatorsKeys as ECK

class ExtraGeometryCalculatorFabric: ExtraSelectableCalculatorFabric {
    override fun createCalculators(context: Context): List<ExtraSelectableCalculatorInfo> {
        val extraDrawables = with(context) {
            mapOf(
                ECK.CALC_SHAPES_POLYGON_ID to R.drawable.ic_polygon,
                ECK.CALC_SHAPES_RIGHT_TRIANGLE_ID to R.drawable.ic_weight,
                ECK.CALC_SHAPES_RECTANGLE_ID to R.drawable.ic_rectangle,
                ECK.CALC_SHAPES_TRAPEZOID_ID to R.drawable.ic_weight,
                ECK.CALC_SHAPES_RHOMBUS_ID to R.drawable.ic_rhombus,
                ECK.CALC_SHAPES_CIRCLE_ID to R.drawable.ic_circle,
                ECK.CALC_SHAPES_ELLIPSE_ID to R.drawable.ic_circle,

                ECK.CALC_BODIES_PRISM_ID to R.drawable.ic_weight,
                ECK.CALC_BODIES_PYRAMID_ID to R.drawable.ic_pyramid,
                ECK.CALC_BODIES_PYRAMID_FRUSTUM_ID to R.drawable.ic_weight,
                ECK.CALC_BODIES_CONE_ID to R.drawable.ic_weight,
                ECK.CALC_BODIES_CONE_FRUSTUM_ID to R.drawable.ic_weight,
                ECK.CALC_BODIES_CYLINDER_ID to R.drawable.ic_weight,
                ECK.CALC_BODIES_SPHERE_ID to R.drawable.ic_weight,
                ECK.CALC_BODIES_ELLIPSOID_ID to R.drawable.ic_weight,
            )
        }

        val extraTitles = with(context) {
            mapOf(
                ECK.CALC_SHAPES_POLYGON_ID to getString(R.string.extra_calculator_polygon_shape_name),
                ECK.CALC_SHAPES_RIGHT_TRIANGLE_ID to getString(R.string.extra_calculator_right_triangle_shapes_name),
                ECK.CALC_SHAPES_RECTANGLE_ID to getString(R.string.extra_calculator_rectangle_shape_name),
                ECK.CALC_SHAPES_TRAPEZOID_ID to getString(R.string.extra_calculator_trapezoid_shape_name),
                ECK.CALC_SHAPES_RHOMBUS_ID to getString(R.string.extra_calculator_rhombus_shape_name),
                ECK.CALC_SHAPES_CIRCLE_ID to getString(R.string.extra_calculator_circle_shape_name),
                ECK.CALC_SHAPES_ELLIPSE_ID to getString(R.string.extra_calculator_ellipse_shape_name),
                ECK.CALC_BODIES_PRISM_ID to getString(R.string.extra_calculator_prism_body_name),
                ECK.CALC_BODIES_PYRAMID_ID to getString(R.string.extra_calculator_pyramid_body_name),
                ECK.CALC_BODIES_PYRAMID_FRUSTUM_ID to getString(R.string.extra_calculator_pyramidFrustum_body_name),
                ECK.CALC_BODIES_CONE_ID to getString(R.string.extra_calculator_cone_body_name),
                ECK.CALC_BODIES_CONE_FRUSTUM_ID to getString(R.string.extra_calculator_coneFrustum_body_name),
                ECK.CALC_BODIES_CYLINDER_ID to getString(R.string.extra_calculator_cylinder_body_name),
                ECK.CALC_BODIES_SPHERE_ID to getString(R.string.extra_calculator_sphere_body_name),
                ECK.CALC_BODIES_ELLIPSOID_ID to getString(R.string.extra_calculator_ellipsoid_body_name)
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
                drawableResId = extraDrawables[calcId],
                previewValues = "",
                type = ExtraCalculatorType.Geometry
            )
        }
    }
}