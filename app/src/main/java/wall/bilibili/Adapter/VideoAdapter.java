package wall.bilibili.Adapter;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import wall.bilibili.R;
import wall.bilibili.info.BaseVideoInfo;
import wall.bilibili.info.VideoInfo;

public class VideoAdapter<T>  extends BaseAdapter{


    class ViewHolder
    {
        ImageView pic;
        TextView title;

    }
    private Context context;
    private List<T> videos;

    public VideoAdapter(Context context, List<T> videos) {
        this.context = context;
        this.videos = videos;
    }

    @Override
    public int getCount() {
        return videos.size();
    }

    @Override
    public Object getItem(int position) {
        return videos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return videos.get(position).hashCode();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder = null;
        if(convertView == null)
        {
            convertView = LayoutInflater.from( context).inflate(R.layout.item_video,null);
            holder = new ViewHolder();

            holder.pic  = convertView.findViewById(R.id.pic);
            holder.title = convertView.findViewById(R.id.title);
            convertView.setTag(holder);


        }
        else {
            holder = (ViewHolder) convertView.getTag();
        }

        BaseVideoInfo info = (BaseVideoInfo) videos.get(position);
        if(info.getPicUrl().startsWith("http"))
            Picasso.with(context).load(Uri.parse(info.getPicUrl())).into(holder.pic);
            else
        Picasso.with(context).load(Uri.parse("http:"+info.getPicUrl())).into(holder.pic);

        System.out.println("ada[ter:"+"http:"+info.getTitle());
        holder.title . setText(position+". "+ info.getTitle());

        return convertView;
    }
}
