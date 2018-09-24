//package com.example.erez0_000.weddingapp.Login_pages.HorizontalRecyclerBusinesses;
//
//import android.content.Context;
//import android.support.annotation.NonNull;
//import android.support.v7.widget.RecyclerView;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ImageView;
//import android.widget.TextView;
//
//import com.bumptech.glide.Glide;
//import com.example.erez0_000.weddingapp.R;
//import com.example.erez0_000.weddingapp.db_classes.Businesses;
//
////import com.firebase.ui.database.FirebaseRecyclerAdapter;
////import com.firebase.ui.database.FirebaseRecyclerOptions;
//import com.google.firebase.database.Query;
//
//public class SuggestionRecyclerAdapter extends
//        FirebaseRecyclerAdapter<Businesses, SuggestionRecyclerAdapter.SuggestionViewHolder> {
//
//    private Context context;
//    private RecyclerView mResultList;
//
//    /**
//     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
//     * {@link FirebaseRecyclerOptions} for configuration options.
//     *
//     * @param options
//     */
//    public SuggestionRecyclerAdapter(@NonNull FirebaseRecyclerOptions<Businesses> options) {
//        super(options);
//
//    }
//
//
//    @Override
//    protected void onBindViewHolder(@NonNull SuggestionViewHolder holder, int position, @NonNull Businesses model) {
//
//        holder.business_region.setText(model.getRegion());
//        holder.business_name.setText(model.getName());
//        holder.business_address.setText(model.getAddress());
//        Glide.with(this.context).load(model.image).into(holder.businessImage);
//    }
//
//    @NonNull
//    @Override
//    public SuggestionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        View view = LayoutInflater.from(parent.getContext())
//                .inflate(R.layout.list_layout, parent, false);
//
//        return new SuggestionRecyclerAdapter.SuggestionViewHolder(view);
//    }
//
//    public class SuggestionViewHolder extends RecyclerView.ViewHolder {
//        private ImageView businessImage;
//        private TextView business_name, business_address, business_region;
//
//        public SuggestionViewHolder(View itemView) {
//            super(itemView);
//            businessImage = itemView.findViewById(R.id.thumbnail);
//            business_address = itemView.findViewById(R.id.address);
//            business_name = itemView.findViewById(R.id.anime_name);
//            business_region  =itemView.findViewById(R.id.region);
//
//        }
//
//    }
//}
//
