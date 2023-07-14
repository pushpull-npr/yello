/**
 * MIT License
 *
 * Copyright (c) 2021 Rodrigo Alves
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

//  https://github.com/racra/smooth-corner-rect-android-compose

package presentation.component.shape


import kotlin.math.*

/**
 * A class representing the points required to draw the curves in a smooth corner.
 * A smooth corner is made up of an arc surrounded by 2 diagonally symmetrical bezier curves.
 * Documentation on the makeup of a smooth curve:
 *
 * @param cornerRadius the size of the corners
 * @param smoothnessAsPercent a percentage representing how smooth the corners will be
 * @param maximumCurveStartDistanceFromVertex the maximum height/width this curve can have
 */
internal class SmoothCorner(
	private val cornerRadius : Float,
	private val smoothnessAsPercent : Int,
	private val maximumCurveStartDistanceFromVertex : Float
) {

	init {
		require(smoothnessAsPercent >= 0) {
			"The value for smoothness can never be negative."
		}
	}

	private val radius = min(cornerRadius, maximumCurveStartDistanceFromVertex)

	private val smoothness = smoothnessAsPercent / 100f

	// Distance from the first point of the curvature to the vertex of the corner
	private val curveStartDistance =
		min(maximumCurveStartDistanceFromVertex, (1 + smoothness) * radius)

	private val shouldCurveInterpolate = radius <= maximumCurveStartDistanceFromVertex / 2

	// This value is used to start interpolating between smooth corners and
	// round corners
	private val interpolationMultiplier =
		(radius - maximumCurveStartDistanceFromVertex / 2) / (maximumCurveStartDistanceFromVertex / 2)

	// Angle at second control point of the bezier curves
	private val angleAlpha =
		if (shouldCurveInterpolate)
			Math.toRadians(45.0 * smoothness).toFloat()
		else
			Math.toRadians(45.0 * smoothness * (1 - interpolationMultiplier)).toFloat()

	// Angle which dictates how much of the curve is going to be a slice of a circle
	private val angleBeta =
		if (shouldCurveInterpolate)
			Math.toRadians(90.0 * (1.0 - smoothness)).toFloat()
		else
			Math.toRadians(90.0 * (1 - smoothness * (1 - interpolationMultiplier))).toFloat()

	private val angleTheta = ((Math.toRadians(90.0) - angleBeta) / 2.0).toFloat()

	// Distance from second control point to end of Bezier curves
	private val distanceE = radius * tan(angleTheta / 2)

	// Distances in the x and y axis used to position end of Bezier
	// curves relative to it's second control point
	private val distanceC = distanceE * cos(angleAlpha)
	private val distanceD = distanceC * tan(angleAlpha)

	// Distances used to position second control point of Bezier curves
	// relative to their first control point
	private val distanceK = sin(angleBeta / 2) * radius
	private val distanceL = (distanceK * sqrt(2.0)).toFloat()
	private val distanceB =
		((curveStartDistance - distanceL) - (1 + tan(angleAlpha)) * distanceC) / 3

	// Distance used to position first control point of Bezier curves
	// relative to their origin
	private val distanceA = 2 * distanceB

	// Represents the outer anchor points of the smooth curve
	val anchorPoint1 = PointRelativeToVertex(
		min(curveStartDistance, maximumCurveStartDistanceFromVertex),
		0f
	)

	// Represents the control point for point1
	val controlPoint1 = PointRelativeToVertex(
		anchorPoint1.distanceToFurthestSide - distanceA,
		0f
	)

	// Represents the control point for point2
	val controlPoint2 = PointRelativeToVertex(
		controlPoint1.distanceToFurthestSide - distanceB,
		0f
	)

	// Represents the inner anchor points of the smooth curve
	val anchorPoint2 = PointRelativeToVertex(
		controlPoint2.distanceToFurthestSide - distanceC,
		distanceD
	)

	val arcSection = Arc(
		radius = radius,
		arcStartAngle = angleTheta,
		arcSweepAngle = angleBeta
	)
}

/**
 * Represents a point positioned relative to a corner vertex, so that it can be used
 * to calculate a smooth curve independently of which quadrant of the rectangle this
 * curve will be inserted in.
 */
internal data class PointRelativeToVertex(
	val distanceToFurthestSide : Float,
	val distanceToClosestSide : Float
)

/**
 * Represents the arc section of a smooth corner curve
 *
 * @param arcStartAngle the start angle of the arc inside the first quadrant of rotation (0º to 90º)
 * @param arcSweepAngle the angle at the center point between the start and end of the arc
 */
internal data class Arc(
	val radius : Float,
	val arcStartAngle : Float,
	val arcSweepAngle : Float
)
