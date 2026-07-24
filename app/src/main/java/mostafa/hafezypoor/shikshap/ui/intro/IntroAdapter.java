package mostafa.hafezypoor.shikshap.ui.intro;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.airbnb.lottie.LottieAnimationView;

import java.util.List;

import mostafa.hafezypoor.shikshap.R;
import mostafa.hafezypoor.shikshap.data.model.IntroModel;

public  class IntroAdapter extends PagerAdapter {
    private Context context;
    private List<IntroModel> list;

    public IntroAdapter(Context context, List<IntroModel> list) {
        this.context = context;
        this.list = list;
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
        LayoutInflater inflater=LayoutInflater.from(context);
        View view=inflater.inflate(R.layout.intro_adapter,container,false);
        TextView title=view.findViewById(R.id.title);
        TextView description=view.findViewById(R.id.description);
        LottieAnimationView animation=view.findViewById(R.id.animation);
        animation.setAnimation(list.get(position).getAnimation());
        title.setText(list.get(position).getTitle());
        description.setText(list.get(position).getDescription());
        if (position==1){
            animation.setSpeed(0.1f);
        }else{
            animation.setSpeed(1f);
        }
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View)object);
    }
}
