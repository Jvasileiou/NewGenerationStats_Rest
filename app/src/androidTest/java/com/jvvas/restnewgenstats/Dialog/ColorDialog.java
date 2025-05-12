package com.jvvas.restnewgenstats.Dialog;

import android.content.Context;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.jvvas.restnewgenstats.R;

public class ColorDialog extends DialogFragment {

    private static final String TAG = "ColorDialog";
    public interface OnInputSelected{
        void sendInput(String input);
    }

    public OnInputSelected mOnInputSelected;

    private View view;
    private ImageView image1,image2,image3,image4,image5,image6,
            image7,image8,image9,image10,image11,image12 ;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.dialog_color, container, false);
        initializeTheColors();
        clickImages();

        return view;
    }

    private void clickImages() {
        image1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setTheSelectedColor("#ffdc00");
            }
        });

        image2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setTheSelectedColor("#f012be");
            }
        });

        image3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setTheSelectedColor("#ffffff");
            }
        });

        image4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setTheSelectedColor("#01ff70");
            }
        });

        image5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setTheSelectedColor("#aaaaaa");
            }
        });

        image6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setTheSelectedColor("#ff4136");
            }
        });

        image7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setTheSelectedColor("#0074d9");
            }
        });

        image8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setTheSelectedColor("#39cccc");
            }
        });

        image9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setTheSelectedColor("#ff8b1b");
            }
        });

        image10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setTheSelectedColor("#23cc40");
            }
        });

        image11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setTheSelectedColor("#ba68c8");
            }
        });

        image12.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setTheSelectedColor("#d3b8ae");
            }
        });
    }

    private void setTheSelectedColor(String colorCode) {
        mOnInputSelected.sendInput(colorCode);
        getDialog().dismiss();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (mOnInputSelected!=null) return;
        try {
            mOnInputSelected = (OnInputSelected) getTargetFragment();
        }catch (ClassCastException e){
            Log.e(TAG,"onCatch Method: ClassCastException : " + e.getMessage() );
        }
    }

    private void initializeTheColors() {
        image1 =  view.findViewById(R.id.imageView_One);
        image2 =  view.findViewById(R.id.imageView_Two);
        image3 =  view.findViewById(R.id.imageView_Three);
        image4 =  view.findViewById(R.id.imageView_Four);
        image5 =  view.findViewById(R.id.imageView_Five);
        image6 =  view.findViewById(R.id.imageView_Six);
        image7 =  view.findViewById(R.id.imageView_Seven);
        image8 =  view.findViewById(R.id.imageView_Eight);
        image9 =  view.findViewById(R.id.imageView_Nine);
        image10 = view.findViewById(R.id.imageView_Ten);
        image11 = view.findViewById(R.id.imageView_Eleven);
        image12 = view.findViewById(R.id.imageView_Twelve);
    }

    public void setmOnInputSelected(OnInputSelected mOnInputSelected) {
        this.mOnInputSelected = mOnInputSelected;
    }

}
