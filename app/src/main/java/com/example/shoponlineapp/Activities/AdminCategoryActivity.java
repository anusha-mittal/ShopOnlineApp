package com.example.shoponlineapp.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.shoponlineapp.R;

public class AdminCategoryActivity extends AppCompatActivity {

    ImageView sweathers,female_dresses,mobiles,watches,tshirts,sports,purses,hats,glasses,headphones,laptops,shoes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_category);

        sweathers=findViewById(R.id.sweather);
        female_dresses=findViewById(R.id.female_dresses);
        mobiles=findViewById(R.id.mobiles);
        watches=findViewById(R.id.watches);
        tshirts=findViewById(R.id.tshirts);
        sports=findViewById(R.id.sports);
        purses=findViewById(R.id.purses);
        hats=findViewById(R.id.hats);
        glasses=findViewById(R.id.glasses);
        headphones=findViewById(R.id.headphones);
        laptops=findViewById(R.id.laptops);
        shoes=findViewById(R.id.shoes);

        sweathers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(AdminCategoryActivity.this, "Sweathers", Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(AdminCategoryActivity.this,AdminAddNewProductActivity.class);
                startActivity(intent);
            }
        });

        female_dresses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(AdminCategoryActivity.this, "Female Dresses", Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(AdminCategoryActivity.this,AdminAddNewProductActivity.class);
                startActivity(intent);
            }
        });

        glasses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(AdminCategoryActivity.this, "Glasses", Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(AdminCategoryActivity.this,AdminAddNewProductActivity.class);
                startActivity(intent);
            }
        });

        hats.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(AdminCategoryActivity.this, "Hats", Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(AdminCategoryActivity.this,AdminAddNewProductActivity.class);
                startActivity(intent);
            }
        });

        headphones.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(AdminCategoryActivity.this, "Headphones", Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(AdminCategoryActivity.this,AdminAddNewProductActivity.class);
                startActivity(intent);
            }
        });

        laptops.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(AdminCategoryActivity.this, "Laptops", Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(AdminCategoryActivity.this,AdminAddNewProductActivity.class);
                startActivity(intent);
            }
        });

        mobiles.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(AdminCategoryActivity.this, "Mobiles", Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(AdminCategoryActivity.this,AdminAddNewProductActivity.class);
                startActivity(intent);
            }
        });

        purses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(AdminCategoryActivity.this, "Purses", Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(AdminCategoryActivity.this,AdminAddNewProductActivity.class);
                startActivity(intent);
            }
        });

        shoes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(AdminCategoryActivity.this, "Shoes", Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(AdminCategoryActivity.this,AdminAddNewProductActivity.class);
                startActivity(intent);
            }
        });

        sports.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(AdminCategoryActivity.this, "Sports", Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(AdminCategoryActivity.this,AdminAddNewProductActivity.class);
                startActivity(intent);
            }
        });

        tshirts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(AdminCategoryActivity.this, "tshirts", Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(AdminCategoryActivity.this,AdminAddNewProductActivity.class);
                startActivity(intent);
            }
        });

        watches.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(AdminCategoryActivity.this, "Watches", Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(AdminCategoryActivity.this,AdminAddNewProductActivity.class);
                startActivity(intent);
            }
        });
    }
}