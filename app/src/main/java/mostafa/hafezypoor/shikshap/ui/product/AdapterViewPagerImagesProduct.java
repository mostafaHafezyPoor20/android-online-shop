package mostafa.hafezypoor.shikshap.ui.product;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.squareup.picasso.Picasso;

import java.util.List;

import mostafa.hafezypoor.shikshap.R;
import mostafa.hafezypoor.shikshap.data.model.ModelAdapterViewPagerImagesProduct;

public class AdapterViewPagerImagesProduct extends PagerAdapter {
    private Context context;
    private List<ModelAdapterViewPagerImagesProduct>images;

    public AdapterViewPagerImagesProduct(Context context, List<ModelAdapterViewPagerImagesProduct> images) {
        this.context = context;
        this.images = images;
    }

    @Override
    public int getCount() {
        return images.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view==object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View view= LayoutInflater.from(context).inflate(R.layout.adapter_view_pager_images_product,container,false);
        ImageView imageProduct=view.findViewById(R.id.imageProduct);
        Picasso.get().load(images.get(position).getProduct_image()).into(imageProduct);
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }
}
