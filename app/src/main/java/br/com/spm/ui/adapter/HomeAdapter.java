package br.com.spm.ui.adapter;

import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.picasso.OkHttp3Downloader;
import com.squareup.picasso.Picasso;

import java.util.List;

import br.com.spm.R;
import br.com.spm.databinding.HomeItemBinding;
import br.com.spm.model.entity.SiteEntity;
import br.com.spm.network.APIClient;
import br.com.spm.network.NetworkConfig;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.HomeHolder> {

    private List<SiteEntity> siteEntityList;
    private HomeAdapterListener listerListener;

    public HomeAdapter(HomeAdapterListener listerListener) {
        this.listerListener = listerListener;
    }

    public void setLogoEntity(List<SiteEntity> siteEntityList) {
        this.siteEntityList = siteEntityList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public HomeHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new HomeHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.home_item, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull HomeHolder homeHolder, int position) {
        homeHolder.bind(siteEntityList.get(position));
        if(position == siteEntityList.size() - 1){
            homeHolder.binding.viewLine.setVisibility(View.GONE);
        }else{
            homeHolder.binding.viewLine.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return siteEntityList != null ? siteEntityList.size() : 0;
    }

    public class HomeHolder extends RecyclerView.ViewHolder {

        HomeItemBinding binding;

        public HomeHolder(@NonNull View itemView) {
            super(itemView);
            binding = DataBindingUtil.bind(itemView);
        }

        public void bind(final SiteEntity siteEntity) {
            Picasso picasso = new Picasso.Builder(itemView.getContext())
                    .downloader(new OkHttp3Downloader(NetworkConfig.getClient()))
                    .build();

            picasso.load(APIClient.API_BASE_URL+"logo/"+siteEntity.getUrl()).into(binding.imageLogo);

            binding.textUrl.setText(siteEntity.getUrl());
            binding.textName.setText(siteEntity.getName());
            binding.textEmail.setText(siteEntity.getEmail());

            binding.relativeContent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listerListener.onSelectedItem(siteEntity);
                }
            });
        }
    }

    public interface HomeAdapterListener {
        void onSelectedItem(SiteEntity siteEntity);
    }
}
