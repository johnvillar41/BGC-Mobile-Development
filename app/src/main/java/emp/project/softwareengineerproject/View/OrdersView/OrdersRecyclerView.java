package emp.project.softwareengineerproject.View.OrdersView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.transition.AutoTransition;
import androidx.transition.TransitionManager;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import emp.project.softwareengineerproject.Interface.IOrders;
import emp.project.softwareengineerproject.Model.OrdersModel;
import emp.project.softwareengineerproject.Presenter.OrdersPresenter;
import emp.project.softwareengineerproject.R;
import emp.project.softwareengineerproject.Services.OrdersService;

public class OrdersRecyclerView extends RecyclerView.Adapter<OrdersRecyclerView.MyViewHolder> {

    List<OrdersModel> list;
    Context context;
    IOrders.IOrdersPresenter presenter;
    OrdersActivityView activity;

    public OrdersRecyclerView(List<OrdersModel> list, Context context, OrdersActivityView activity) {
        this.list = list;
        this.context = context;
        this.activity = activity;
        this.presenter = new OrdersPresenter(activity, activity);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {
        final OrdersModel model = getItem(position);

        holder.customer_name.setText(model.getCustomer_name());
        holder.customer_email.setText(model.getCustomer_email());
        holder.txt_total.setText(model.getOrder_total_price());
        holder.txt_order_id.setText(model.getOrder_id());
        holder.txt_order_date.setText(model.getOrder_date());
        /**if (model.getOrder_status().equals(STATUS.PENDING.getStatus())) {
         holder.layout_background_color.setBackgroundColor(Color.parseColor(COLORS.GREEN.getColor()));
         } else if (model.getOrder_status().equals(STATUS.CANCELLED.getStatus())) {
         holder.layout_background_color.setBackgroundColor(Color.parseColor(COLORS.RED.getColor()));
         } else if (model.getOrder_status().equals(STATUS.FINISHED.getStatus())) {
         holder.layout_background_color.setBackgroundColor(Color.parseColor(COLORS.BLUE.getColor()));
         }*/

        holder.order_status.setText(model.getOrder_status());

        holder.imageView_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popup = new PopupMenu(context, holder.imageView_menu);
                popup.inflate(R.menu.menu_orders);
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {

                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.page_pending_orders:
                                presenter.onMenuPendingClicked(model.getOrder_id());
                                if (!model.getOrder_status().equals(STATUS.PENDING.getStatus())) {
                                    list.remove(position);
                                    notifyItemRemoved(position);
                                    notifyItemRangeChanged(position, list.size());
                                    presenter.addNotification(STATUS.PENDING_NOTIF.getStatus(), STATUS.NOTIF_CONTENT.getStatus());
                                }
                                return true;
                            case R.id.page_finished_orders:
                                presenter.onMenuFinishClicked(model.getOrder_id());
                                if (!model.getOrder_status().equals(STATUS.FINISHED.getStatus())) {
                                    list.remove(position);
                                    notifyItemRemoved(position);
                                    notifyItemRangeChanged(position, list.size());
                                    presenter.addNotification(STATUS.FINISHED_NOTIF.getStatus(), STATUS.NOTIF_CONTENT.getStatus());
                                }
                                return true;
                            case R.id.page_cancelled_orders:
                                presenter.onMenuCancelClicked(model.getOrder_id());
                                if (!model.getOrder_status().equals(STATUS.CANCELLED.getStatus())) {
                                    list.remove(position);
                                    notifyItemRemoved(position);
                                    notifyItemRangeChanged(position, list.size());
                                    presenter.addNotification(STATUS.CANCELLED_NOTIF.getStatus(), STATUS.NOTIF_CONTENT.getStatus());
                                }
                                return true;
                            default:
                                return false;
                        }
                    }
                });
                popup.show();
            }
        });
        holder.expandable_fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (holder.expandableLayout.getVisibility() == View.VISIBLE) {
                    TransitionManager.beginDelayedTransition(holder.cardView,
                            new AutoTransition());
                    holder.expandableLayout.setVisibility(View.GONE);
                    holder.expandable_fab.setImageResource(R.drawable.animation_down);

                } else {
                    TransitionManager.beginDelayedTransition(holder.cardView,
                            new AutoTransition());
                    holder.expandableLayout.setVisibility(View.VISIBLE);
                    holder.expandable_fab.setImageResource(R.drawable.animation_up);
                    OrdersService service = new OrdersService(model);
                    try {
                        LinearLayoutManager linearLayoutManager
                                = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
                        OrdersSpecificRecyclerView adapter = new OrdersSpecificRecyclerView(context,service.getCustomerSpecificOrders(model.getCustomer_email(),model.getOrder_date()));
                        holder.recyclerView.setLayoutManager(linearLayoutManager);
                        holder.recyclerView.setAdapter(adapter);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });



    }



    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.custom_adapter_orders, parent, false);
        return new OrdersRecyclerView.MyViewHolder(view);
    }

    private enum COLORS {
        GREEN("#024C05"),
        BLUE("#0000ff"),
        RED("#FF0000");
        private String color;

        COLORS(String color) {
            this.color = color;
        }

        private String getColor() {
            return color;
        }
    }

    private enum STATUS {
        PENDING("Processing"),
        CANCELLED("Cancelled"),
        FINISHED("Finished"),

        PENDING_NOTIF("Order moved to pending"),
        CANCELLED_NOTIF("Order cancelled"),
        FINISHED_NOTIF("Order is finished"),

        NOTIF_CONTENT("Order Update");

        private String status;

        STATUS(String status) {
            this.status = status;
        }

        private String getStatus() {
            return status;
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public OrdersModel getItem(int position) {
        return list.get(position);
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView txt_order_date, txt_order_id, customer_name, customer_email, txt_total, order_status;
        ImageView imageView_menu;
        FloatingActionButton expandable_fab;
        LinearLayout expandableLayout;
        CardView cardView;
        RecyclerView recyclerView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            txt_order_date = itemView.findViewById(R.id.txt_date);
            txt_order_id = itemView.findViewById(R.id.txt_order_number);
            customer_name = itemView.findViewById(R.id.txt_customer_name);
            customer_email = itemView.findViewById(R.id.txt_customer_email);
            txt_total = itemView.findViewById(R.id.txt_total_value);
            order_status = itemView.findViewById(R.id.txt_status);
            imageView_menu = itemView.findViewById(R.id.image_menu_orders);
            expandable_fab = itemView.findViewById(R.id.expandable_fab);
            expandableLayout = itemView.findViewById(R.id.expandable_Layout);
            cardView = itemView.findViewById(R.id.cardView_Orders);
            recyclerView = itemView.findViewById(R.id.recyclerView_specific_orders);
        }
    }
}
