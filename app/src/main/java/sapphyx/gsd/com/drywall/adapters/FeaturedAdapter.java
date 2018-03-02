package sapphyx.gsd.com.drywall.adapters;

import android.app.Application;
import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.RecyclerView;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.prof.rssparser.Article;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;

import sapphyx.gsd.com.drywall.Drywall;
import sapphyx.gsd.com.drywall.R;
import sapphyx.gsd.com.drywall.activity.DetailsActivity;
import sapphyx.gsd.com.drywall.activity.FeatureInfoActivity;
import sapphyx.gsd.com.drywall.util.AnimationHelper;
import sapphyx.gsd.com.drywall.util.PaletteTransformation;

/**
 * Created by ry on 2/12/18.
 */

public class FeaturedAdapter extends RecyclerView.Adapter<FeaturedAdapter.ViewHolder> {

    private ArrayList<Article> articles;

    private int rowLayout;
    private Context mContext;

    private boolean usePalette = true;

    public FeaturedAdapter(ArrayList<Article> list, int rowLayout, Context context) {

        this.articles = list;
        this.rowLayout = rowLayout;
        this.mContext = context;
    }


    @Override
    public long getItemId(int item) {
        // TODO Auto-generated method stub
        return item;
    }

    public void clearData() {
        if (articles != null)
            articles.clear();
    }

    @Override
    public FeaturedAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(rowLayout, viewGroup, false);
        return new FeaturedAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final FeaturedAdapter.ViewHolder viewHolder, final int position) {

        WindowManager wm = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        int imageWidth = (width / 2);

        viewHolder.title.setText(articles.get(position).getTitle());
        viewHolder.author.setText(articles.get(position).getAuthor());

        Picasso.with(mContext).setIndicatorsEnabled(false);

        Picasso.with(mContext)
                .load(articles.get(position).getImage())
                .resize(imageWidth, imageWidth)
                .centerCrop()
                .transform(PaletteTransformation.instance())
                .into(viewHolder.image,
                        new PaletteTransformation.PaletteCallback(viewHolder.image) {
                            @Override
                            public void onSuccess(Palette palette) {
                                if (usePalette) {
                                    if (palette != null) {
                                        Palette.Swatch wallSwatch = palette.getDominantSwatch();
                                        if (wallSwatch != null ) {
                                            viewHolder.titleBg.setBackgroundColor(wallSwatch.getRgb());
                                            viewHolder.titleBg.setAlpha((float) 0.5);
                                            viewHolder.title.setTextColor(wallSwatch.getBodyTextColor());
                                            viewHolder.title.setAlpha(1);
                                            viewHolder.author.setTextColor(wallSwatch.getBodyTextColor());
                                            viewHolder.author.setAlpha(1);
                                            viewHolder.info.setColorFilter(wallSwatch.getTitleTextColor());
                                            viewHolder.download.setColorFilter(wallSwatch.getTitleTextColor());
                                        }
                                    }
                                }
                            }

                            @Override
                            public void onError() {
                            }
                        });

        viewHolder.info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Intent intent = new Intent(mContext, FeatureInfoActivity.class)
                        .putExtra("wallDescription", articles.get(position).getDescription())
                        .putExtra("wallTitle", articles.get(position).getTitle());
                mContext.startActivity(intent);

            }
        });

        viewHolder.download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                File direct = new File(Environment.getExternalStorageDirectory()
                        + "/Drywall");

                if (!direct.exists()) {
                    direct.mkdirs();
                }

                DownloadManager mgr = (DownloadManager) mContext.getSystemService(Context.DOWNLOAD_SERVICE);

                Uri downloadUri = Uri.parse(articles.get(position).getImage());
                DownloadManager.Request request = new DownloadManager.Request(
                        downloadUri);

                request.setAllowedNetworkTypes(
                        DownloadManager.Request.NETWORK_WIFI
                                | DownloadManager.Request.NETWORK_MOBILE)
                        .setAllowedOverRoaming(false).setTitle("Drywall")
                        .setDescription("Requesting wallpaper and downloading...")
                        .setDestinationInExternalPublicDir("/Drywall", articles.get(position).getTitle() + ".png");

                mgr.enqueue(request);


            }
        });

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Intent intent = new Intent(mContext, DetailsActivity.class)
                        .putExtra("wall", articles.get(position).getImage())
                        .putExtra("wallTitle", articles.get(position).getTitle());
                mContext.startActivity(intent);
            }
        });
        return;
    }

    @Override
    public int getItemCount() {
        return articles == null ? 0 : articles.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView title, author;
        ImageView image, download, info;
        LinearLayout titleBg;

        public ViewHolder(View itemView) {
            super(itemView);

            AnimationHelper.quickViewReveal(itemView.findViewById(R.id.card_image), 300);
            AnimationHelper.quickViewReveal(itemView.findViewById(R.id.title_bkg), 400);

            title = itemView.findViewById(R.id.card_title);
            author = itemView.findViewById(R.id.card_author);
            image = itemView.findViewById(R.id.card_image);
            download = itemView.findViewById(R.id.image_download);
            info = itemView.findViewById(R.id.image_info);
            titleBg = itemView.findViewById(R.id.title_bkg);
        }
    }
}