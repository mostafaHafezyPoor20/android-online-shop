package mostafa.hafezypoor.shikshap.ui.product;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import mostafa.hafezypoor.shikshap.R;
import mostafa.hafezypoor.shikshap.data.model.ModelComments;

public class AdapterFProductComments extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final int COMMENT_NOT_ANSWERED = 0;
    private final int COMMENT_ANSWERED = 1;

    private Context context;
    private List<ModelComments> list;
    private String comment_id;

    public AdapterFProductComments(Context context, List<ModelComments> list,String comment_id) {
        this.context = context;
        this.list = list;
        this.comment_id=comment_id;
    }

    @Override
    public int getItemViewType(int position) {
        if (list.get(position).getAdminAnswer().isEmpty()) {
            return COMMENT_NOT_ANSWERED;
        } else {
            return COMMENT_ANSWERED;
        }
    }

    @NonNull
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == COMMENT_ANSWERED) {
            return new ViewHolderCommentAnswered(LayoutInflater.from(context).inflate(R.layout.view_holder_comment_answered, parent, false));
        } else {
            return new ViewHolderCommentNotAnswered(LayoutInflater.from(context).inflate(R.layout.view_holder_comment_not_answered, parent, false));
        }
    }

    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ViewHolderCommentNotAnswered) {
            ViewHolderCommentNotAnswered h = (ViewHolderCommentNotAnswered) holder;
            h.name.setText(list.get(position).getName());
            h.comment.setText(list.get(position).getComment().replace("\\n", "\n").replace("n\\", "\n"));
        } else if (holder instanceof ViewHolderCommentAnswered) {
            ViewHolderCommentAnswered h = (ViewHolderCommentAnswered) holder;
            h.name.setText(list.get(position).getName());
            h.comment.setText(list.get(position).getComment().replace("\\n", "\n").replace("n\\", "\n"));
            h.answer.setText(list.get(position).getAdminAnswer());
            if (comment_id!=null){
                if (comment_id.equals(list.get(position).getId())){
                    animationItem(h.itemView);
                }
            }
        }
    }

    public int getItemCount() {
        return list.size();
    }

    class ViewHolderCommentNotAnswered extends RecyclerView.ViewHolder {
        TextView name, comment;

        public ViewHolderCommentNotAnswered(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            comment = itemView.findViewById(R.id.comment);
        }
    }

    class ViewHolderCommentAnswered extends RecyclerView.ViewHolder {
        TextView name, comment, answer;

        public ViewHolderCommentAnswered(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            comment = itemView.findViewById(R.id.comment);
            answer = itemView.findViewById(R.id.answer);
        }
    }

    public int findPositionComment(String comment_id) {
        int position = 0;
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getId().equals(comment_id)){
              position=i;
            break;
             }
        }

        return position;
        }
public void animationItem(View itemView){
    ObjectAnimator scaleUpx=ObjectAnimator.ofFloat(itemView,"scaleX",1f,1.08f);
    ObjectAnimator scaleUpY=ObjectAnimator.ofFloat(itemView,"scaleY",1f,1.08f);
    scaleUpx.setDuration(300);
    scaleUpY.setDuration(300);

    ObjectAnimator shakeX=ObjectAnimator.ofFloat(itemView,"translationX",0,15,-15,10,-10,5,-5,0);
    shakeX.setDuration(600);

    ObjectAnimator scaleDownX=ObjectAnimator.ofFloat(itemView,"scaleX",1.08f,1f);
    ObjectAnimator scaleDownY=ObjectAnimator.ofFloat(itemView,"scaleY",1.08f,1f);
    scaleDownX.setDuration(300);
    scaleDownY.setDuration(300);

    AnimatorSet animatorSet=new AnimatorSet();
    animatorSet.play(scaleUpx).with(scaleUpY);
    animatorSet.play(shakeX).with(scaleUpx);
    animatorSet.play(scaleDownX).with(scaleDownY).after(shakeX);
    animatorSet.start();


}
    }