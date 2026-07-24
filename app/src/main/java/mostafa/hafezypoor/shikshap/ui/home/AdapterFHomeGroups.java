package mostafa.hafezypoor.shikshap.ui.home;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

import mostafa.hafezypoor.shikshap.R;
import mostafa.hafezypoor.shikshap.data.model.FHomeModelProduct;
import mostafa.hafezypoor.shikshap.data.model.FHomeModelGroup;
import mostafa.hafezypoor.shikshap.ui.product.Product;

public class AdapterFHomeGroups extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private int TOP_SHOW=0;
    private int GROUP_SHOW=1;
    private Activity activity;
    private List<FHomeModelGroup>listGroup;
    private List<FHomeModelProduct>listTopShow;
    public AdapterFHomeGroups(Activity activity, List<FHomeModelGroup> listGroup  ,List<FHomeModelProduct>listTopShow,IEvent iEvent) {
        this.activity = activity;
        this.listGroup = listGroup;
        this.listTopShow=listTopShow;
        this.iEvent=iEvent;
    }
    @Override
    public int getItemViewType(int position) {
        if (position==0&&!listTopShow.isEmpty()){
            return TOP_SHOW;
        }else{
            return GROUP_SHOW;
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       if (viewType==GROUP_SHOW){
           return new ViewHolderGroupShow(LayoutInflater.from(activity).inflate(R.layout.adapter_fhome_groups,parent,false));
       }else{
           return new ViewHolderTopShow(LayoutInflater.from(activity).inflate(R.layout.adapter_show_product_in_top_show,parent,false));
       }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof ViewHolderTopShow){
                ViewHolderTopShow h= (ViewHolderTopShow) holder;
                AdapterViewPagerTopShow adapterViewPagerTopShow=new AdapterViewPagerTopShow(activity, listTopShow, new AdapterViewPagerTopShow.IEvent() {
                    @Override
                    public void onClick() {
                        Intent intent=new Intent(activity, Product.class);
                        Bundle bundle=new Bundle();
                        bundle.putString("product_id",listTopShow.get(h.indicator.getSelectedTabPosition()).getId());
                        intent.putExtras(bundle);
                        activity.startActivity(intent);
                    }
                });
            h.viewPagerTopShow.setAdapter(adapterViewPagerTopShow);
            h.indicator.setupWithViewPager(h.viewPagerTopShow,true);
            h.viewPagerTopShow.setPageTransformer(true, new ViewPager.PageTransformer() {
                @Override
                public void transformPage(@NonNull View page, float position) {
                    //cube
                 //   page.setPivotX(position < 0 ? page.getWidth() : 0);
                 //   page.setPivotY(page.getHeight() * 0.5f);
                  //  page.setRotationY(-90f*position);
                    //fade
                  page.setTranslationX(-position*page.getWidth());
                    page.setAlpha(1-Math.abs(position));

                    //depth
                 /* if (position > 0&&position < 1){
                        page.setAlpha(1-position);
                        page.setTranslationX(page.getWidth()*-position);
                        page.setScaleX(0.75f + (1-Math.abs(position))*0.25f);
                        page.setScaleY(0.75f+(1-Math.abs(position))*0.25f);
                    }else{
                        page.setAlpha(1);
                    }*/
                }
            });
           h.viewPagerTopShow.setOffscreenPageLimit(10);
            Handler handler=new Handler();
            Runnable runnable=new Runnable() {
                @Override
                public void run() {
              int currentItem=h.viewPagerTopShow.getCurrentItem();
              int totalItem=adapterViewPagerTopShow.getCount();
              if (currentItem < totalItem -1){
                  h.viewPagerTopShow.setCurrentItem(currentItem+1,true);
              }else{
                  h.viewPagerTopShow.setCurrentItem(0,true);
              }
              handler.postDelayed(this,3000);
                }
            };
            handler.postDelayed(runnable,3000);
            h.viewPagerTopShow.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                }

                @Override
                public void onPageSelected(int position) {
                      handler.removeCallbacks(runnable);
                      handler.postDelayed(runnable,3000);
                }

                @Override
                public void onPageScrollStateChanged(int state) {

                }
            });
        }else if (holder instanceof ViewHolderGroupShow){
                if (!listTopShow.isEmpty()){
                    position--;
                }
                ViewHolderGroupShow h= (ViewHolderGroupShow) holder;
                h.group_name.setText(listGroup.get(position).getName());
                showProducts(listGroup.get(position).getId(),h.list);
                h.group_name.setOnClickListener(showAllProductGroup(listGroup.get(position).getId(),listGroup.get(position).getName()));
                h.imgShowAll.setOnClickListener(showAllProductGroup(listGroup.get(position).getId(),listGroup.get(position).getName()));
                h.textShowAll.setOnClickListener(showAllProductGroup(listGroup.get(position).getId(),listGroup.get(position).getName()));
            }

    }

    @Override
    public int getItemCount() {
     if (listTopShow.isEmpty()){
         return listGroup.size();
     }else{
         return listGroup.size()+1;
     }

    }
    class ViewHolderGroupShow extends RecyclerView.ViewHolder{
        TextView group_name,textShowAll;
        RecyclerView list;
        ImageView imgShowAll;
        public ViewHolderGroupShow(@NonNull View itemView) {
            super(itemView);
            group_name=itemView.findViewById(R.id.group_name);
            list=itemView.findViewById(R.id.list);
            imgShowAll=itemView.findViewById(R.id.imgShowAll);
            textShowAll=itemView.findViewById(R.id.textShowAll);
        }
    }
    class ViewHolderTopShow extends RecyclerView.ViewHolder{
           ViewPager viewPagerTopShow;
           TabLayout indicator;
        public ViewHolderTopShow(@NonNull View itemView) {
            super(itemView);
            viewPagerTopShow=itemView.findViewById(R.id.viewPagerTopShow);
            indicator=itemView.findViewById(R.id.tabLayoutIndicator);
        }
    }
    private void showProducts(String group_id,RecyclerView list){
        iEvent.showProducts(group_id,list);
    }



    public View.OnClickListener showAllProductGroup(String group_id,String group_name){
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iEvent.showAllProductGroup(group_id,group_name);
            }
        };
    }
     public interface IEvent{
         void showAllProductGroup(String group_id,String group_name);
         void showProducts(String group_id,RecyclerView list);

     }
     private IEvent iEvent;
}
