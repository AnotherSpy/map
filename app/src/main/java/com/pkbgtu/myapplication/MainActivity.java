package com.pkbgtu.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.app.Activity;

import com.yandex.mapkit.Animation;
import com.yandex.mapkit.MapKitFactory;
import com.yandex.mapkit.geometry.Point;
import com.yandex.mapkit.map.CameraPosition;

import com.yandex.mapkit.map.GeoObjectSelectionMetadata;
import com.yandex.mapkit.map.PlacemarkMapObject;
import com.yandex.mapkit.mapview.MapView;
import com.yandex.runtime.image.ImageProvider;
import com.yandex.runtime.ui_view.ViewProvider;


public class MainActivity extends Activity {

    private final String MAPKIT_API_KEY = "b1f778dc-7eaf-47f0-923c-2324b1ae1761";
    private final Point TARGET_LOCATION = new Point(59.945933, 30.320045);

    private MapView mapView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        MapKitFactory.setApiKey(MAPKIT_API_KEY);
        MapKitFactory.initialize(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mapView = (MapView)findViewById(R.id.mapview);
        mapView.getMap().move(
                new CameraPosition(TARGET_LOCATION, 14.0f, 0.0f, 0.0f),
                new Animation(Animation.Type.SMOOTH, 5),
                null);
        PlacemarkMapObject o = mapView.getMap().getMapObjects().addPlacemark(TARGET_LOCATION, ImageProvider.fromBitmap(drawSimpleBitmap("Hello")));
    }

    public Bitmap drawSimpleBitmap(String text) {
        int picSize = 300;
        Bitmap bitmap = Bitmap.createBitmap(picSize, picSize, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        // отрисовка плейсмарка
        Paint paint = new Paint();
        paint.setColor(Color.GREEN);
        paint.setStyle(Paint.Style.FILL);
        canvas.drawCircle(picSize / 2, picSize / 2, picSize / 2, paint);
        // отрисовка текста
        paint.setColor(Color.WHITE);
        paint.setAntiAlias(true);
        paint.setTextSize(15);
        paint.setTextAlign(Paint.Align.CENTER);
        canvas.drawText(text, picSize / 2,
                picSize / 2 - ((paint.descent() + paint.ascent()) / 2), paint);
        return bitmap;
    }

    @Override
    protected void onStop() {
        // Вызов onStop нужно передавать инстансам MapView и MapKit.
        mapView.onStop();
        MapKitFactory.getInstance().onStop();
        super.onStop();
    }

    @Override
    protected void onStart() {
        // Вызов onStart нужно передавать инстансам MapView и MapKit.
        super.onStart();
        MapKitFactory.getInstance().onStart();
        mapView.onStart();
    }

}
