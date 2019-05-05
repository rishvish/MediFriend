package com.example.rishabhvishwakarma.medifriend.medical;

import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AbsListView;
import android.widget.LinearLayout;

import com.balysv.materialripple.MaterialRippleLayout;
import com.example.rishabhvishwakarma.medifriend.R;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FirstAidFragment extends Fragment implements RecyclerView.OnItemTouchListener {

    public OnFirstAidListFragmentInteractionListener mListener;
    private RecyclerViewAdapter recyclerViewAdapter;
    private SearchView searchView;
    private RecyclerView list;
    private Context context;
    private List<String> firstAidList;
    private boolean isHomeAsUpShown;
    private ActionBar actionBar;
    private String previousTitle,previousSubTitle;
    public FirstAidFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onResume() {
        setHasOptionsMenu(true);
        super.onResume();
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_first_aid, container, false);

//        SET ACTION BAR
        actionBar = ((AppCompatActivity)getActivity()).getSupportActionBar();
        if (actionBar != null) {
            isHomeAsUpShown = (actionBar.getDisplayOptions() & ActionBar.DISPLAY_HOME_AS_UP) != 0;
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_back);
            previousTitle = actionBar.getTitle().toString();
            previousSubTitle = actionBar.getSubtitle().toString();
            actionBar.setSubtitle(R.string.first_aid_subtitle);
            actionBar.setTitle(R.string.first_aid_info);
        }

        list = view.findViewById(R.id.recyclerview);
        firstAidList = Arrays.asList(getResources().getStringArray(R.array.first_aid));
        context = list.getContext();
        recyclerViewAdapter = new RecyclerViewAdapter(false, getContext(), firstAidList);
        list.setLayoutManager(new LinearLayoutManager(context));
        list.setAdapter(recyclerViewAdapter);

        list.addOnItemTouchListener(new RecyclerViewOnItemClickListener(getContext(), new RecyclerViewOnItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                int itemCount = recyclerViewAdapter.getItemCount();
                if (position != itemCount - 1) {
                    AppCompatTextView textView = ((MaterialRippleLayout)((LinearLayout) list.findViewHolderForAdapterPosition(position).itemView).getChildAt(0)).getChildView();
                    mListener.onListFragmentInteraction(true, textView.getText().toString());
                } else {
//                    SEARCH ONLINE
                    View v = inflater.inflate(R.layout.web_search_dialog, new ViewGroup(getContext()) {
                        @Override
                        protected void onLayout(boolean b, int i, int i1, int i2, int i3) {
                        }
                    }, false);
                    final TextInputEditText editText = v.findViewById(R.id.web_search_dialog_search_bar);
                    if (searchView != null)
                        if (searchView.getQuery() != null)
                            editText.append(searchView.getQuery());
                    AlertDialog.Builder dialog = new AlertDialog.Builder(getContext());
                    dialog.setView(v)
                            .setTitle("Search online")
                            .setPositiveButton("search", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    String query = null;
                                    try {
                                        query = URLEncoder.encode(editText.getText().toString(), "utf-8");
                                    } catch (UnsupportedEncodingException e) {
                                        e.printStackTrace();
                                    }
                                    String url = "http://www.google.com/search?q=" + query;
                                    Intent intent = new Intent(Intent.ACTION_VIEW);
                                    intent.setData(Uri.parse(url));
                                    startActivity(intent);
                                }
                            })
                            .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    dialogInterface.dismiss();
                                }
                            })
                            .show();
                    final InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            editText.requestFocus();
                            imm.showSoftInput(editText, InputMethodManager.SHOW_IMPLICIT);
                        }
                    }, 500);
                }
            }
        }));

        list.addOnScrollListener(new RecyclerView.OnScrollListener() {
            private boolean scrolling = false;
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    if (scrolling)
                        scrolling = false;
                }
                else if (newState == RecyclerView.SCROLL_STATE_DRAGGING) {
                    if (!scrolling)
                        scrolling = true;
                }
                else if (newState == AbsListView.OnScrollListener.SCROLL_STATE_FLING) {
                    if (!scrolling)
                        scrolling = true;
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (scrolling) {
                    if (dy > 5) {
//                    SCROLLING UP
                        mListener.onListFragmentInteraction(false, "up");
                    } else if (dy < - 5){
//                    SCROLLING DOWN
                        mListener.onListFragmentInteraction(false, "down");
                    }
                }
            }
        });

        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.menu_first_aid, menu);
        SearchManager searchManager = (SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);
        MenuItem menuItem = menu.findItem(R.id.action_search);
        menuItem.setActionView(new SearchView(getContext()));
        searchView = (SearchView) menuItem.getActionView();

        searchView.setSearchableInfo(searchManager.getSearchableInfo(getActivity().getComponentName()));
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                final List<String> filteredList = new ArrayList<>();

                for (int x = 0; x < firstAidList.size(); x++) {
                    final String text = firstAidList.get(x).toLowerCase();
                    if (text.contains(newText))
                        filteredList.add(firstAidList.get(x));
                }
                filteredList.add("Could not find what you were looking for?\nClick here to search online");

                list.setLayoutManager(new LinearLayoutManager(context));
                recyclerViewAdapter = new RecyclerViewAdapter(false, getContext(), filteredList);
                list.setAdapter(recyclerViewAdapter);
                recyclerViewAdapter.notifyDataSetChanged();
                return true;
            }
        });
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFirstAidListFragmentInteractionListener) {
            mListener = (OnFirstAidListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFirstAidListFragmentInteractionListener");
        }
    }

    @Override
    public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
        return false;
    }

    @Override
    public void onTouchEvent(RecyclerView rv, MotionEvent e) {

    }

    @Override
    public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

    }

    public interface OnFirstAidListFragmentInteractionListener {
        void onListFragmentInteraction(boolean flag, String item);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (!isHomeAsUpShown){
            actionBar.setDisplayHomeAsUpEnabled(false);
        }
        actionBar.setTitle(previousTitle);
        actionBar.setSubtitle(previousSubTitle);
        super.onDestroy();

    }

}
