package mostafa.hafezypoor.shikshap.ui.common;

import android.content.Context;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.google.android.material.bottomsheet.BottomSheetDialog;

import mostafa.hafezypoor.shikshap.R;

public class BottomSheetDialogLoading extends BottomSheetDialog {
    public BottomSheetDialogLoading(@NonNull Context context,String title) {
        super(context, R.style.AppBottomSheetDialogTheme);
        setContentView(R.layout.bottom_sheet_dialog_loading);
        setCancelable(false);
        ((TextView)findViewById(R.id.title)).setText(title);
    }
    public void setTitle(String title){
        ((TextView)findViewById(R.id.title)).setText(title);
    }
}
