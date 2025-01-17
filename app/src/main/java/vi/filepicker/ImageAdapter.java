package vi.filepicker;

import android.content.Context;
import android.net.Uri;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;

/**
 * Created by droidNinja on 29/07/16.
 */
public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.FileViewHolder> {

    private final ImageAdapterListener imageAdapterListener;
    private final ArrayList<Uri> paths;
    private final Context context;
    private int imageSize;
    public ImageAdapter(Context context, ArrayList<Uri> paths, ImageAdapterListener imageAdapterListener) {
        this.context = context;
        this.paths = paths;
        this.imageAdapterListener = imageAdapterListener;
        setColumnNumber(context, 3);
    }

    private void setColumnNumber(Context context, int columnNum) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics metrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(metrics);
        int widthPixels = metrics.widthPixels;
        imageSize = widthPixels / columnNum;
    }

    @Override
    public FileViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView =
                LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout, parent, false);

        return new FileViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(FileViewHolder holder, int position) {
        final Uri path = paths.get(position);
        Glide.with(context)
                .load(path)
                .apply(RequestOptions.centerCropTransform()
                        .dontAnimate()
                        .override(imageSize, imageSize)
                        .placeholder(droidninja.filepicker.R.drawable.image_placeholder))
                .thumbnail(0.5f)
                .into(holder.imageView);
        holder.itemView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                imageAdapterListener.onItemClick(path);
            }
        });
    }

    @Override
    public int getItemCount() {
        return paths.size();
    }

    public interface ImageAdapterListener {
        void onItemClick(Uri uri);
    }

    public static class FileViewHolder extends RecyclerView.ViewHolder {

        AppCompatImageView imageView;

        public FileViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.iv_photo);
        }
    }
}
