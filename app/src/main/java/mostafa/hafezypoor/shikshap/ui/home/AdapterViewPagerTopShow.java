package mostafa.hafezypoor.shikshap.ui.home;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import eightbitlab.com.blurview.BlurTarget;
import eightbitlab.com.blurview.BlurView;
import mostafa.hafezypoor.shikshap.R;
import mostafa.hafezypoor.shikshap.data.model.FHomeModelProduct;
import mostafa.hafezypoor.shikshap.ui.product.Product;

public class AdapterViewPagerTopShow extends PagerAdapter {
    private Context context;
    private List<FHomeModelProduct> list;
    public interface  IEvent{
        void  onClick();
    }
    private IEvent iEvent;
    public AdapterViewPagerTopShow(Context context, List<FHomeModelProduct> list,IEvent iEvent) {
        this.context = context;
        this.list = list;
        this.iEvent=iEvent;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view==object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View view= LayoutInflater.from(context).inflate(R.layout.adapter_view_pager_top_show,container,false);
        ImageView img=view.findViewById(R.id.img);
        TextView productName=view.findViewById(R.id.productName);
        BlurView blurView=view.findViewById(R.id.blurView);
        BlurTarget blurTarget=view.findViewById(R.id.blurTarget);
        ViewGroup rootView= (ViewGroup)((Activity)context).getWindow().getDecorView();
       blurView.setupWith(blurTarget).setFrameClearDrawable(rootView.getBackground()).setBlurRadius(30f).setBlurEnabled(true).setOverlayColor(Color.parseColor("#40FFFFFF"));
        Picasso.get().load(list.get(position).getProduct_image()).into(img);
        productName.setText(list.get(position).getProduct_name());
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iEvent.onClick();
            }
        });
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View)object);
    }


}
