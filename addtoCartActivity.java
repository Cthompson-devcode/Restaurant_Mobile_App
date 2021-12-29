package com.example.donnasdiner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.donnasdiner.Adapter.BreakfastAdapter;
import com.example.donnasdiner.Adapter.cartAdapter;
import com.example.donnasdiner.Database.restaurantRepository;
import com.example.donnasdiner.Entities.BreakfastEntity;
import com.example.donnasdiner.Entities.cartEntity;
import com.example.donnasdiner.ViewModel.breakfastViewModel;
import com.example.donnasdiner.ViewModel.cartViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.w3c.dom.Text;

import java.util.List;

public class addtoCartActivity extends AppCompatActivity {
    public static double EXTRA_TOTAL_PRICE =
            11111111111.00;

    public static Integer EDIT_BREAKFAST_REQUEST = 2;


    private double totaltogetpassed;
    private double totalTaxestogetpassed;
    private double totalsubtotaltogetpassed;
    private double totalordertosubmit;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private Button nextProcessButton;
    private TextView textTotalAmount;

    private cartViewModel cartViewModel;

    private restaurantRepository restaurantRepository;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addto_cart);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic__cancel);


        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        cartAdapter adapter = new cartAdapter();
        recyclerView.setAdapter(adapter);


        cartViewModel = ViewModelProviders.of(this).get(cartViewModel.class);
        cartViewModel.getAllcart().observe(this, new Observer<List<cartEntity>>() {
            @Override
            public void onChanged(@Nullable List<cartEntity> cart) {

                adapter.setcart(cart);

                Intent intent = getIntent();

               double orderTotal= adapter.getTotal(cartViewModel.getAllcart1());



                System.out.println("THIS IS THE ORDERTOTAL IN ADDTOCART ==========" +orderTotal);

                setTitle("Order Total Price   " + orderTotal);

                EXTRA_TOTAL_PRICE = orderTotal;
                totaltogetpassed = orderTotal;

                totalTaxestogetpassed = totaltogetpassed *.07;

                totalsubtotaltogetpassed = totalTaxestogetpassed + totaltogetpassed;

                totalordertosubmit = totalsubtotaltogetpassed;





            }
        });

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {

                cartEntity cartEntity = adapter.getCartAt(viewHolder.getAdapterPosition());
                cartViewModel cartViewModel = new cartViewModel(getApplication());




               cartViewModel.delete(adapter.getCartAt(viewHolder.getAdapterPosition()));


                double orderTotal= adapter.getTotal(cartViewModel.getAllcart1());



                System.out.println("THIS IS THE ORDERTOTAL IN ADDTOCART ==========" +orderTotal);

                setTitle("Order Subtotal:    $" + orderTotal);

                totaltogetpassed = orderTotal;

                finish();
                startActivity(getIntent());


                Toast.makeText(addtoCartActivity.this, "Breakfast Item Deleted! ", Toast.LENGTH_SHORT).show();
            }


        }).attachToRecyclerView(recyclerView);



        adapter.setonItemClickListener(new cartAdapter.onItemClickListener() {
            @Override

            public void onItemClick(cartEntity cartEntity) {
                //sending data that was clicked
                Intent intent = new Intent(addtoCartActivity.this, viewOrderDetailsActivity.class);
                intent.putExtra(viewOrderDetailsActivity.EXTRA_BREAKFAST_ID, String.valueOf(cartEntity.getCartID()));
                intent.putExtra(viewOrderDetailsActivity.EXTRA_BREAKFAST_TITLE, cartEntity.getDishName());
                intent.putExtra(viewOrderDetailsActivity.EXTRA_BREAKFAST_PRICE, String.valueOf(cartEntity.getDishPrice()));

                startActivityForResult(intent, EDIT_BREAKFAST_REQUEST);

            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();


    }

    public void go_to_checkout(View view) {

            Intent intent = new Intent(addtoCartActivity.this, checkoutActivity.class);
        intent.putExtra(checkoutActivity.EXTRA_TOTAL_PRICE, String.valueOf(totaltogetpassed));
        intent.putExtra(checkoutActivity.EXTRA_TOTAL_TAXES, String.valueOf(totalTaxestogetpassed));
        intent.putExtra(checkoutActivity.EXTRA_SUBTOTAL, String.valueOf(totalsubtotaltogetpassed));
        intent.putExtra(checkoutActivity.EXTRA_TOTAL_ORDER_FINAL, String.valueOf(totalordertosubmit));

        System.out.println("THIS IS HTE TOALTTOGETPASSED variabel ====== " +totaltogetpassed);

            startActivity(intent);

    }
}