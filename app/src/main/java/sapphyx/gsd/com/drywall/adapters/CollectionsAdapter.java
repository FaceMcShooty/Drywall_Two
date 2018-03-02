package sapphyx.gsd.com.drywall.adapters;

import android.content.Context;
import android.graphics.Point;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.RecyclerView;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.prof.rssparser.Article;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import sapphyx.gsd.com.drywall.Drywall;
import sapphyx.gsd.com.drywall.R;
import sapphyx.gsd.com.drywall.util.AnimationHelper;
import sapphyx.gsd.com.drywall.util.PaletteTransformation;

/**
 * Created by ry on 2/12/18.
 */

public class CollectionsAdapter extends RecyclerView.Adapter<CollectionsAdapter.ViewHolder> {

    private ArrayList<Article> articles;

    private int rowLayout;
    private Context mContext;

    private boolean usePalette = true;

    public CollectionsAdapter(ArrayList<Article> list, int rowLayout, Context context) {

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
    public CollectionsAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(rowLayout, viewGroup, false);
        return new CollectionsAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final CollectionsAdapter.ViewHolder viewHolder, final int position) {

        WindowManager wm = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        int imageWidth = (width / 2);

        viewHolder.title.setText(articles.get(position).getTitle());
        viewHolder.desc.setText(articles.get(position).getDescription());

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
                                            viewHolder.desc.setTextColor(wallSwatch.getBodyTextColor());
                                            viewHolder.desc.setAlpha(1);
                                        }
                                    }
                                }
                            }

                            @Override
                            public void onError() {

                            }
                        });
        return;
    }

    @Override
    public int getItemCount() {
        return articles == null ? 0 : articles.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        TextView desc;
        ImageView image;
        FrameLayout titleBg;


        public ViewHolder(View itemView) {
            super(itemView);

            AnimationHelper.quickViewReveal(itemView.findViewById(R.id.card_image), 300);
            AnimationHelper.quickViewReveal(itemView.findViewById(R.id.title_bkg), 400);

            title = itemView.findViewById(R.id.card_title);
            desc = itemView.findViewById(R.id.card_desc);
            image = itemView.findViewById(R.id.card_image);
            titleBg = itemView.findViewById(R.id.title_bkg);


        }
    }
}