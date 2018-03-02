package sapphyx.gsd.com.drywall.fragments;

import android.animation.ObjectAnimator;
import android.app.WallpaperManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.ScaleAnimation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import net.qiujuer.genius.blur.StackBlur;

import java.io.IOException;

import jahirfiquitiva.libs.fabsmenu.FABsMenu;
import jahirfiquitiva.libs.fabsmenu.FABsMenuListener;
import jahirfiquitiva.libs.fabsmenu.TitleFAB;
import sapphyx.gsd.com.drywall.R;
import sapphyx.gsd.com.drywall.activity.About;
import sapphyx.gsd.com.drywall.activity.Settings;
import sapphyx.gsd.com.drywall.util.AnimationHelper;
import sapphyx.gsd.com.drywall.util.SettingsProvider;

import static sapphyx.gsd.com.drywall.activity.MainActivityBase.ARGS_INSTANCE;

/**
 * Created by ry on 2/11/18.
 */

public class Device extends Fragment {

    public Context context;
    private Drawable wallpaperDrawable, lockscreenDrawable;
    private ImageView wallpaper, lockscreen;
    private CardView lockscreenCard;
    private LinearLayout infoWalls, infoSections, infoTools;
    private FrameLayout source, rate, apps;

    private TitleFAB photos, blur, reset, setCustomOffset, setSingleOffest;

    private static final int DURATION = 800;
    private static final int OFFSET = 0;
    private String URL_SOURCE = "https://github.com/rgocal/Drywall/tree/Drywall2";
    private String URL_RATE = "https://play.google.com/store/apps/details?id=sapphyx.gsd.com.drywall";
    private String URL_APPS = "https://play.google.com/store/apps/dev?id=8934848548747347540";

    public Device() {
    }

    public static Device newInstance(int instance) {
        Bundle args = new Bundle();
        args.putInt(ARGS_INSTANCE, instance);
        Device fragment = new Device();
        fragment.setArguments(args);
        return fragment;
    }

    int getScreenHeight() {
        DisplayMetrics displaymetrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        return displaymetrics.heightPixels;
    }

    public void animateOnScreen(View view) {
        final int screenHeight = getScreenHeight();
        ObjectAnimator animator = ObjectAnimator.ofFloat(view, "y", screenHeight, (screenHeight * 0.8F));
        animator.setInterpolator(new DecelerateInterpolator());
        animator.start();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.wallpaper_background, container, false);
        setHasOptionsMenu(true);

        final WallpaperManager wallpaperManager = WallpaperManager.getInstance(getContext());

        wallpaper = v.findViewById(R.id.wallpaperView);
        lockscreen = v.findViewById(R.id.lockscreenView);
        lockscreenCard = v.findViewById(R.id.lockscreen_Card);

        reset = v.findViewById(R.id.clear_wallpaper);
        setSingleOffest = v.findViewById(R.id.single_offset);
        setCustomOffset = v.findViewById(R.id.custom_offset);
        photos = v.findViewById(R.id.photos);
        blur = v.findViewById(R.id.blur);

        infoWalls = v.findViewById(R.id.info_walls);
        infoSections = v.findViewById(R.id.info_sections);
        infoTools = v.findViewById(R.id.info_tools);

        source = v.findViewById(R.id.app_container);
        rate = v.findViewById(R.id.rate_container);
        apps = v.findViewById(R.id.more_container);

        source.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sendIntent = new Intent
                        (Intent.ACTION_VIEW, Uri.parse(URL_SOURCE));
                getActivity().startActivity(Intent.createChooser(sendIntent, "Open With"));
            }
        });

        rate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sendIntent = new Intent
                        (Intent.ACTION_VIEW, Uri.parse(URL_RATE));
                getActivity().startActivity(Intent.createChooser(sendIntent, "Open With"));
            }
        });

        apps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sendIntent = new Intent
                        (Intent.ACTION_VIEW, Uri.parse(URL_APPS));
                getActivity().startActivity(Intent.createChooser(sendIntent, "Open With"));
            }
        });

        final int xStep = 1;
        final int yStep = 1;

        DisplayMetrics metrics = new DisplayMetrics();
        WindowManager windowManager = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
        windowManager.getDefaultDisplay().getMetrics(metrics);
        final int height = metrics.heightPixels;
        final int width = metrics.widthPixels;

        final FABsMenu menu = v.findViewById(R.id.fabs_menu);
        menu.setMenuUpdateListener(new FABsMenuListener() {
            @Override
            public void onMenuClicked(FABsMenu fabsMenu) {
                super.onMenuClicked(fabsMenu);
            }
        });


        if (menu.getVisibility() == View.INVISIBLE) {
            menu.setVisibility(View.VISIBLE);
            animateOnScreen(menu);
        }


        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    wallpaperManager.clear();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        photos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(
                            "content://media/internal/images/media"));
                    startActivity(intent);
                }catch (Exception e){
                    Log.e("Activity", "Gallery not found");
                    Toast.makeText(getActivity(), "Could not connect to Gallery", Toast.LENGTH_SHORT).show();
                }

            }
        });

        blur.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                        try {
                            WallpaperManager.getInstance(getActivity()).setBitmap(StackBlur.blur(drawableToBitmap(wallpaperManager.getDrawable()), SettingsProvider.getInt(getActivity(), "pref_blur", 20), false));
                            Toast.makeText(getActivity(), "Done",
                                    Toast.LENGTH_SHORT).show();
                        } catch (Exception e) {
                            Toast.makeText(getActivity(), "Error, Cannot Blur Current Wallpaper",
                                    Toast.LENGTH_LONG).show();
                        }
                    }
                });

                setCustomOffset.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        wallpaperManager.setWallpaperOffsetSteps(xStep, yStep);
                        wallpaperManager.suggestDesiredDimensions(SettingsProvider.getInt(getActivity(), "customWidth", width), SettingsProvider.getInt(getActivity(), "customHeight", height));


                        Drawable wallpaperDrawable = wallpaperManager.getDrawable();
                        final Bitmap bm = ((BitmapDrawable) wallpaperDrawable).getBitmap();
                        Bitmap bitmap = Bitmap.createScaledBitmap(bm, SettingsProvider.getInt(getActivity(), "customWidth", width), SettingsProvider.getInt(getActivity(), "customHeight", height), true);

                        try {
                            wallpaperManager.setBitmap(bitmap);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    }
                });

                setSingleOffest.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        wallpaperManager.setWallpaperOffsetSteps(xStep, yStep);
                        wallpaperManager.suggestDesiredDimensions(width, height);

                        Drawable wallpaperDrawable = wallpaperManager.getDrawable();
                        final Bitmap bm = ((BitmapDrawable) wallpaperDrawable).getBitmap();
                        Bitmap bitmap = Bitmap.createScaledBitmap(bm, width, height, true);

                        try {
                            wallpaperManager.setBitmap(bitmap);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    }
                });

        AnimationHelper.quickViewReveal(v.findViewById(R.id.wallpaperView), 300);

        AnimationHelper.quickViewReveal(v.findViewById(R.id.info_walls), 400);
        AnimationHelper.quickViewReveal(v.findViewById(R.id.info_sections), 400);
        AnimationHelper.quickViewReveal(v.findViewById(R.id.info_tools), 400);


        AnimationHelper.quickViewReveal(v.findViewById(R.id.app_container), 500);

        wallpaperDrawable = wallpaperManager.getDrawable();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N_MR1) {
            lockscreenDrawable = wallpaperManager.getBuiltInDrawable(WallpaperManager.FLAG_LOCK);
            lockscreen.setImageDrawable(lockscreenDrawable);
            //Lets hide this for now...untill its working
            lockscreenCard.setVisibility(View.GONE);
        } else {
            lockscreenCard.setVisibility(View.GONE);
        }

        wallpaper.setImageDrawable(wallpaperDrawable);
        return  v;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.device_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.refresh:
                ScaleAnimation zoomAnimation = new ScaleAnimation(0.1f, 1.0f, 0.1f, 1.0f, 0.5f,0.5f);
                zoomAnimation.setDuration(DURATION);
                zoomAnimation.setStartOffset(OFFSET);

                WallpaperManager wallpaperManager = WallpaperManager.getInstance(getContext());
                Drawable wallpaperDrawable = wallpaperManager.getDrawable();
                final Bitmap bm = ((BitmapDrawable) wallpaperDrawable).getBitmap();

                wallpaper.startAnimation(zoomAnimation);
                wallpaper.setImageBitmap(bm);
                Toast.makeText(getContext(), "Refreshing", Toast.LENGTH_SHORT).show();
                break;

            case R.id.actions:
                Intent intent = new Intent(getContext(), WallpaperActions.class);
                startActivity(intent);
                break;

            case R.id.about:
                Intent about_intent = new Intent(getContext(), About.class);
                startActivity(about_intent);
                break;

            case R.id.settings:
                Intent settings_intent = new Intent(getContext(), Settings.class);
                startActivity(settings_intent);
                break;
        }
        return true;

    }

    public static Bitmap drawableToBitmap(Drawable drawable) {
        if (drawable == null)
            return null;

        Bitmap bitmap = null;

        if (drawable instanceof BitmapDrawable) {
            BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
            if (bitmapDrawable.getBitmap() != null) {
                return bitmapDrawable.getBitmap();
            }
        }

        if (drawable.getIntrinsicWidth() <= 0 || drawable.getIntrinsicHeight() <= 0) {
            bitmap = Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888); // Single color bitmap will be created of 1x1 pixel
        } else {
            bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        }

        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);
        return bitmap;
    }
}