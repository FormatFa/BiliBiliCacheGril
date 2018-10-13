package wall.bilibili.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import wall.bilibili.Pop.CommentPop;
import wall.bilibili.R;
import wall.bilibili.info.RepliesInfo;
import wall.bilibili.utils.FormatFaUtils;

public class CommentAdapter extends BaseAdapter {

    class ViewHolder
    {
        ImageView icon;
        TextView name;
        TextView info;
        TextView content;

    }

    private Context context;
    private RepliesInfo info;

    public CommentAdapter(Context context, RepliesInfo info) {
        this.context = context;
        this.info = info;
    }

    @Override
    public int getCount() {
        return info.getReplies().size();
    }

    @Override
    public Object getItem(int position) {
        return info.getReplies().get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if(convertView == null)
        {
           convertView= LayoutInflater.from(context).inflate(R.layout.item_comment,null);
            holder = new ViewHolder();
            holder.icon = convertView.findViewById(R.id.icon);
            holder.name = convertView.findViewById(R.id.name);
            holder.info = convertView.findViewById(R.id.info);
            holder.content = convertView.findViewById(R.id.content);
            convertView.setTag(holder);
        }
        else
        {
            holder = (ViewHolder) convertView.getTag();
        }

        RepliesInfo item = info.getReplies().get(position);
        holder.name.setText(item.getMember().getUname());
        holder.content.setText(item.getMessage());
        holder.info.setText("#"+item.getFloor());


        if(item.getCount()>0)
        {
            holder.info.append(" 查看更多("+item.getCount()+")");
        }
        if(item.getVip().getVipType()==2)
        {
            holder.name.setTextColor(Color.RED);
            holder.name.append(" (大会员到期时间:"+ FormatFaUtils.stampToStr(item.getVip().getVipDueDate())+")");
        }
        else
            holder.name.setTextColor(Color.WHITE);
        if(CommentPop.isShowAvatar)
        Picasso.with(context).load(Uri.parse(item.getMember().getAvatar())).into(holder.icon);





        return convertView;
    }
}
