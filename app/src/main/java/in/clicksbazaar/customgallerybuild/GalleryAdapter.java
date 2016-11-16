package in.clicksbazaar.customgallerybuild;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by SUNIL on 11/16/2016.
 */
public class GalleryAdapter extends RecyclerView.Adapter<GalleryAdapter.ViewHolder> {
    Context mContext;
    int count;
    ArrayList<Bitmap> mBitmapList;
    ArrayList<String> mPathList;


    public GalleryAdapter(Context mContext, int count, ArrayList<Bitmap> mBitmapList, ArrayList<String> mPathList) {
        this.mContext = mContext;
        this.count = count;
        this.mBitmapList = mBitmapList;
        this.mPathList = mPathList;
    }

    @Override
    public GalleryAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = View.inflate(mContext, R.layout.gridview,null);
        ViewHolder holder = new ViewHolder(itemView);
        return holder;
    }

    @Override
    public void onBindViewHolder(GalleryAdapter.ViewHolder holder, int position) {

        holder.mGalleryImage.setId(position);

        //create a file to write bitmap data
        File f = new File(mContext.getCacheDir(), ""+position);
        try {
            f.createNewFile();
            //Convert bitmap to byte array
            Bitmap bitmap = mBitmapList.get(position);
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, bos);
            byte[] bitmapdata = bos.toByteArray();

        //write the bytes in file
            FileOutputStream fos = new FileOutputStream(f);
            fos.write(bitmapdata);
            fos.flush();
            fos.close();

            Picasso.with(mContext)
                    .load(f)
                    .into(holder.mGalleryImage);
        } catch (IOException e) {
            e.printStackTrace();
        }


        /*
        * Without using picasso library
        */
       // holder.mGalleryImage.setImageBitmap(mBitmapList.get(position));
    }

    @Override
    public int getItemCount() {
        return count;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView mGalleryImage;
        public ViewHolder(View itemView) {
            super(itemView);
            mGalleryImage = (ImageView)itemView.findViewById(R.id.thumbImage);
        }
    }
}
