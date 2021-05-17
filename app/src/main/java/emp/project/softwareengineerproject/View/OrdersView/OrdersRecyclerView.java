package emp.project.softwareengineerproject.View.OrdersView;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.transition.AutoTransition;
import androidx.transition.TransitionManager;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.sql.SQLException;
import java.util.List;

import emp.project.softwareengineerproject.Interface.IOrders;
import emp.project.softwareengineerproject.Model.Bean.OrdersModel;
import emp.project.softwareengineerproject.Model.Bean.SpecificOrdersModel;
import emp.project.softwareengineerproject.Model.Database.Services.OrdersService;
import emp.project.softwareengineerproject.Presenter.OrdersPresenter;
import emp.project.softwareengineerproject.R;

public class OrdersRecyclerView extends RecyclerView.Adapter<OrdersRecyclerView.MyViewHolder> {

    List<OrdersModel> list;
    Context context;
    IOrders.IOrdersPresenter presenter;
    OrdersActivityView activity;

    public OrdersRecyclerView(List<OrdersModel> list, Context context, OrdersActivityView activity) {
        this.list = list;
        this.context = context;
        this.activity = activity;
        this.presenter = new OrdersPresenter(activity, OrdersService.getInstance());
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {
        final OrdersModel model = getItem(position);
        holder.customer_name.setText(model.getUserID());
        holder.txt_total.setText(model.getOrderTotalPrice());
        holder.txt_order_id.setText(model.getOrderID());
        holder.txt_order_date.setText(model.getOrderDate().toString());
        holder.order_status.setText(model.getOrderStatus());
        holder.imageView_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popup = new PopupMenu(context, holder.imageView_menu);
                popup.inflate(R.menu.menu_orders);
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        if (item.getItemId() == R.id.page_pending_orders) {
                            presenter.onMenuPendingClicked(model.getOrderID());
                            if (!model.getOrderStatus().equals(STATUS.PENDING.getStatus())) {
                                list.remove(position);
                                notifyItemRemoved(position);
                                notifyItemRangeChanged(position, list.size());
                                presenter.addNotification(STATUS.PENDING_NOTIF, STATUS.NOTIF_CONTENT.getStatus());
                            }
                            return true;
                        } else if (item.getItemId() == R.id.page_finished_orders) {
                            AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context);
                            dialogBuilder.setTitle("Finsish Item");
                            dialogBuilder.setIcon(R.drawable.ic_move);
                            dialogBuilder.setMessage("Are you sure you want to finish this order?");
                            dialogBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    presenter.onMenuFinishClicked(model.getOrderID());
                                    if (!model.getOrderStatus().equals(STATUS.FINISHED.getStatus())) {
                                        list.remove(position);
                                        notifyItemRemoved(position);
                                        notifyItemRangeChanged(position, list.size());
                                        presenter.addNotification(STATUS.FINISHED_NOTIF, STATUS.NOTIF_CONTENT.getStatus());
                                    }
                                }
                            });
                            dialogBuilder.setNegativeButton("No", null);
                            dialogBuilder.show();
                            return true;
                        } else if (item.getItemId() == R.id.page_cancelled_orders) {
                            presenter.onMenuCancelClicked(model.getOrderID());
                            if (!model.getOrderStatus().equals(STATUS.CANCELLED.getStatus())) {
                                list.remove(position);
                                notifyItemRemoved(position);
                                notifyItemRangeChanged(position, list.size());
                                presenter.addNotification(STATUS.CANCELLED_NOTIF, STATUS.NOTIF_CONTENT.getStatus());
                            }
                            return true;
                        }
                        return false;
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
                    OrdersService service = OrdersService.getInstance(model);
                    Thread thread = new Thread(new Runnable() {
                        @Override
                        public void run() {
                            ((Activity) context).runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    holder.progress_bar_specific_orders.setVisibility(View.VISIBLE);
                                }
                            });
                            LinearLayoutManager linearLayoutManager
                                    = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
                            try {
                                List<SpecificOrdersModel> ordersModels = service.getCustomerSpecificOrders(model.getOrderID());
                                ((Activity) context).runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        OrdersSpecificRecyclerView adapter = new OrdersSpecificRecyclerView(context, ordersModels);
                                        holder.recyclerView.setLayoutManager(linearLayoutManager);
                                        holder.recyclerView.setAdapter(adapter);
                                        holder.progress_bar_specific_orders.setVisibility(View.INVISIBLE);
                                    }
                                });
                            } catch (ClassNotFoundException e) {
                                e.printStackTrace();
                            } catch (SQLException throwables) {
                                throwables.printStackTrace();
                            }
                        }
                    });
                    thread.start();
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

    public enum STATUS {
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

        public String getStatus() {
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
        ProgressBar progress_bar_specific_orders;

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
            progress_bar_specific_orders = itemView.findViewById(R.id.progress_bar_specific_orders);
        }
    }
}
