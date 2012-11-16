/**
 * Copyright 2011 The PlayN Authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package playn.android;

import android.util.Log;
import android.view.Gravity;
import android.widget.LinearLayout;

public class AndroidLayoutView extends LinearLayout {

  private GameActivity activity;

  public AndroidLayoutView(GameActivity activity) {
    super(activity);
    this.activity = activity;
    setBackgroundColor(0xFF000000);
    setGravity(Gravity.CENTER);
  }

  @Override
  public void onSizeChanged(int w, int h, int oldw, int oldh) {
    super.onSizeChanged(w, h, oldw, oldh);
    Log.d("playn", "Screen size changed [width=" + w + ", height=" + h +
          ", orient=" + getResources().getConfiguration().orientation + "]");
    AndroidPlatform platform = activity.platform();
    if (platform == null) {
      AndroidGraphics.setStartingScreenSize(w, h);
    }
  }
}
