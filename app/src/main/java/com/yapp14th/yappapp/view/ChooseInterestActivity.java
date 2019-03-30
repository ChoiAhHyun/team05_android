package com.yapp14th.yappapp.view;

import android.os.Bundle;


import com.yapp14th.yappapp.Base.BaseActivity;
import com.yapp14th.yappapp.R;

//import com.yapp14th.yappapp.adapter.InterestAdapter;

public class ChooseInterestActivity extends BaseActivity {
//    private InterestAdapter adapter;

    @Override
    protected int getLayout() {
        return R.layout.activity_choose_interest;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        ArrayList<InterestItem> items = new ArrayList<>();
//        items.add(new InterestItem("0"));
//        items.add(new InterestItem("1"));
//        items.add(new InterestItem("2"));
//        items.add(new InterestItem("3"));
//        items.add(new InterestItem("4"));
//
//        RecyclerView recyclerView = findViewById(R.id.rv_interest);
//        adapter = new InterestAdapter(items);
//
//        SwipeableTouchHelperCallback swipeableTouchHelperCallback =
//                new SwipeableTouchHelperCallback(new OnItemSwiped() {
//                    @Override public void onItemSwiped() {
//                        adapter.removeTopItem();
//                    }
//
//                    @Override public void onItemSwipedLeft() {
//                        Log.e("SWIPE", "LEFT");
//                    }
//
//                    @Override public void onItemSwipedRight() {
//                        Log.e("SWIPE", "RIGHT");
//                    }
//
//                    @Override
//                    public void onItemSwipedUp() {
//
//                    }
//
//                    @Override
//                    public void onItemSwipedDown() {
//
//                    }
//                }){
//                    @Override
//                    public int getAllowedSwipeDirectionsMovementFlags(RecyclerView.ViewHolder viewHolder) {
//                        return ItemTouchHelper.RIGHT | ItemTouchHelper.LEFT;
//                    }
//                };
//
//        final ItemTouchHelper itemTouchHelper = new ItemTouchHelper(swipeableTouchHelperCallback);
//        itemTouchHelper.attachToRecyclerView(recyclerView);
//        recyclerView.setLayoutManager(new SwipeableLayoutManager().setAngle(10)
//                .setAnimationDuratuion(450)
//                .setMaxShowCount(3)
//                .setScaleGap(0.1f)
//                .setTransYGap(0));
//        recyclerView.setAdapter(adapter);
//
//        itemTouchHelper.swipe(recyclerView.findViewHolderForAdapterPosition(0), ItemTouchHelper.DOWN);
////        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(swipeableTouchHelperCallback);
////        itemTouchHelper.attachToRecyclerView(recyclerView);
////
////        recyclerView.setLayoutManager(new SwipeableLayoutManager());
////        recyclerView.setAdapter(adapter);
//        Log.e("LIST", items.size() + "");
    }
}
