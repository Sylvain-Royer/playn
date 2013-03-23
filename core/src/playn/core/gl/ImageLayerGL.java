/**
 * Copyright 2010 The PlayN Authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */
package playn.core.gl;

import playn.core.Asserts;
import playn.core.Image;
import playn.core.ImageLayer;
import playn.core.InternalTransform;
import playn.core.Tint;

public class ImageLayerGL extends LayerGL implements ImageLayer {

  private float width, height;
  private boolean widthSet, heightSet;
  private ImageGL img;

  public ImageLayerGL(GLContext ctx) {
    super(ctx);
  }

  public ImageLayerGL(GLContext ctx, Image img) {
    this(ctx);
    setImage(img);
  }

  @Override
  public void destroy() {
    super.destroy();
    setImage(null);
  }

  @Override
  public void clearHeight() {
    heightSet = false;
  }

  @Override
  public void clearWidth() {
    widthSet = false;
  }

  @Override
  public Image image() {
    return img;
  }

  @Override
  public void setHeight(float height) {
    Asserts.checkArgument(height > 0, "Height must be > 0");

    heightSet = true;
    this.height = height;
  }

  @Override
  public ImageLayer setImage(Image img) {
    Asserts.checkArgument(img == null || img instanceof ImageGL);
    // avoid releasing and rereferencing image if nothing changes
    if (this.img != img) {
      if (this.img != null)
        this.img.release();
      this.img = (ImageGL) img;
      if (this.img != null)
        this.img.reference();
    }
    return this;
  }

  @Override
  public void setWidth(float width) {
    Asserts.checkArgument(width > 0, "Width must be > 0");

    widthSet = true;
    this.width = width;
  }

  @Override
  public void setSize(float width, float height) {
    Asserts.checkArgument(width > 0 && height > 0,
                          "Width and height must be > 0 (got %dx%d)", width, height);

    widthSet = true;
    this.width = width;
    heightSet = true;
    this.height = height;
  }

  @Override
  public void paint(InternalTransform curTransform, int curTint, GLShader curShader) {
    if (!visible() || img == null) return;

    if (tint != Tint.NOOP_TINT)
      curTint = Tint.combine(curTint, tint);
    img.draw((shader == null) ? curShader : shader, localTransform(curTransform),
             0, 0, width(), height(), curTint);
  }

  @Override
  public float width() {
    Asserts.checkNotNull(img, "Image must not be null");
    return widthSet ? width : img.width();
  }

  @Override
  public float height() {
    Asserts.checkNotNull(img, "Image must not be null");
    return heightSet ? height : img.height();
  }

  @Override
  public float scaledWidth() {
    return scaleX() * width();
  }

  @Override
  public float scaledHeight() {
    return scaleY() * height();
  }
}
