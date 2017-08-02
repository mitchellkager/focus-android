/* -*- Mode: Java; c-basic-offset: 4; tab-width: 4; indent-tabs-mode: nil; -*-
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

package org.mozilla.focus.fragment;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialog;
import android.support.v7.app.AppCompatDialogFragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.mozilla.focus.R;
import org.mozilla.focus.open.AppAdapter;
import org.mozilla.focus.open.OpenWithFragment;
import org.mozilla.telemetry.Telemetry;

public class FindInPageFragment extends AppCompatDialogFragment
            implements TextWatcher, View.OnClickListener {
    public static final String FRAGMENT_TAG = "find_in_page";

    public static FindInPageFragment newInstance() {
        final FindInPageFragment fragment = new FindInPageFragment();

        return fragment;
    }

    @Override
    public void onPause() {
        getActivity().getSupportFragmentManager()
                .beginTransaction()
                .remove(this)
                .commitAllowingStateLoss();

        super.onPause();
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        ContextThemeWrapper wrapper = new ContextThemeWrapper(getContext(), android.R.style.Theme_Material_Light);

        @SuppressLint("InflateParams") // This View will have its params ignored anyway:
        final View view = LayoutInflater.from(wrapper).inflate(R.layout.fragment_find_in_page, null);

        final Dialog dialog = new FindInPageFragment.CustomWidthBottomSheetDialog(wrapper);
        dialog.setContentView(view);

        return dialog;
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        // ignore
    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        // ignore
    }

    @Override
    public void afterTextChanged(Editable editable) {
        // find instances of editable.toString()
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.find_prev: {
                break;
            }

            case R.id.find_next: {

            }

            case R.id.find_close: {

            }
        }
    }

    static class CustomWidthBottomSheetDialog extends BottomSheetDialog {
        private View contentView;

        public CustomWidthBottomSheetDialog(@NonNull Context context) {
            super(context);
        }

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

            // The support library makes the bottomsheet full width on all devices (and then uses a 16:9
            // keyline). On tablets, the system bottom sheets use a narrower width - lets do that too:
            if (getContext().getResources().getBoolean(R.bool.is_tablet)) {
                int width = getContext().getResources().getDimensionPixelSize(R.dimen.tablet_bottom_sheet_width);
                getWindow().setLayout(width, ViewGroup.LayoutParams.MATCH_PARENT);
            }
        }

        @Override
        public void setContentView(View contentView) {
            super.setContentView(contentView);

            this.contentView = contentView;
        }

        @Override
        public void setContentView(@LayoutRes int layoutResId) {
            throw new IllegalStateException("CustomWidthBottomSheetDialog only supports setContentView(View)");
        }

        @Override
        public void setContentView(View view, ViewGroup.LayoutParams params) {
            throw new IllegalStateException("CustomWidthBottomSheetDialog only supports setContentView(View)");
        }

        @Override
        public void show() {
            if (getContext().getResources().getBoolean(R.bool.is_tablet)) {
                final int peekHeight = getContext().getResources().getDimensionPixelSize(R.dimen.tablet_bottom_sheet_peekheight);

                BottomSheetBehavior<View> bsBehaviour = BottomSheetBehavior.from((View) contentView.getParent());
                bsBehaviour.setPeekHeight(peekHeight);
            }

            super.show();
        }
    }
}
