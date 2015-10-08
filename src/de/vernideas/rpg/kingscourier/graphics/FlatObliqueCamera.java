package de.vernideas.rpg.kingscourier.graphics;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector3;

/**
 * A parallel projection with the ground (X/Y axes) being flat while the depth
 * (Z) is shown by extending along the Y axis and shortening by a constant
 * value.
 */
public class FlatObliqueCamera extends OrthographicCamera {
	private final static float DEFAULT_ZSCALE = 2 / 3f;

	public final float zScale;
	public final Vector3 targetPoint = new Vector3();

	public FlatObliqueCamera() {
		super();
		zScale = DEFAULT_ZSCALE;
	}

	public FlatObliqueCamera(float scale) {
		super();
		zScale = scale;
	}

	public FlatObliqueCamera(float vpWidth, float vpHeight) {
		super();
		viewportWidth = vpWidth;
		viewportHeight = vpHeight;
		zScale = DEFAULT_ZSCALE;
		update();
	}

	public FlatObliqueCamera(float vpWidth, float vpHeight, float scale) {
		super();
		viewportWidth = vpWidth;
		viewportHeight = vpHeight;
		zScale = scale;
		update();
	}

	private final Vector3 tmp = new Vector3();

	@Override public void update(boolean updateFrustum) {
		projection.setToOrtho(zoom * -viewportWidth / 2, zoom * (viewportWidth / 2), zoom * -(viewportHeight / 2), zoom * viewportHeight / 2, near, far);
		// projection.val[Matrix4.M12] = zScale * 2 / viewportHeight / zoom;
		// projection.val[Matrix4.M13] = zScale * (far + near) / viewportHeight
		// / zoom;
		view.setToLookAt(position, tmp.set(position).add(direction), up);
		view.val[Matrix4.M31] = 0.01f; // Skew the top part
		view.val[Matrix4.M00] *= 1.01f;
		combined.set(projection);
		Matrix4.mul(combined.val, view.val);

		if( updateFrustum ) {
			invProjectionView.set(combined);
			Matrix4.inv(invProjectionView.val);
			frustum.update(invProjectionView);
		}
	}

	@Override public void lookAt(Vector3 target) {
		targetPoint.set(target);
		super.lookAt(target);
	}

	@Override public void lookAt(float x, float y, float z) {
		targetPoint.set(x, y, z);
		super.lookAt(x, y, z);
	}

}
