package com.example.ninja.ui.create;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import com.example.ninja.R;
import com.example.ninja.model.Appliance;
import com.example.ninja.model.ApplianceModel;
import com.example.ninja.model.Category;
import com.example.ninja.model.CategoryModel;
import com.example.ninja.model.Recipe;
import com.example.ninja.model.RecipeModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.squareup.picasso.Picasso;
import java.util.List;
import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;
public class CreateFragment extends Fragment {

    private CreateViewModel createViewModel;
    ImageView recipeImage;
    ImageButton editImage;
    EditText recipeName;
    EditText prepTime;
    EditText cookTime;
    Button saveBtn;
    ProgressBar spinner;
    Button categoryBtn;
    Button applianceBtn;
    EditText instructions;
    String categoryId;
    String applianceId;
    FirebaseUser user;
    public LiveData<List<Category>> categories;
    public LiveData<List<Appliance>> appliances;
    CreateFragmentArgs bundle;
    Boolean editMode;



    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        createViewModel = new ViewModelProvider(this).get(CreateViewModel.class);
        View root =  inflater.inflate(R.layout.fragment_create, container, false);

        user = FirebaseAuth.getInstance().getCurrentUser();
        recipeImage = root.findViewById(R.id.Create_Recipe_Img);
        editImage = root.findViewById(R.id.Create_Edit_Img_Button);
        recipeName = root.findViewById(R.id.Create_Recipe_Name);
        prepTime = root.findViewById(R.id.Create_Recipe_Prep_Time);
        cookTime = root.findViewById(R.id.Create_Recipe_Cook_Time);
        saveBtn = root.findViewById(R.id.Create_Recipe_Save_Button);
        spinner = root.findViewById(R.id.Create_Spinner);
        spinner.setVisibility(View.INVISIBLE);
        categoryBtn = root.findViewById(R.id.Create_Recipe_Category);
        applianceBtn  = root.findViewById(R.id.Create_Recipe_Appliance);
        instructions = root.findViewById(R.id.Create_Recipe_Instructions);
        appliances= createViewModel.getApplianceList();
        categories = createViewModel.getCategoryList();
        editMode = false;



        if(!getArguments().isEmpty())
        {
            bundle = CreateFragmentArgs.fromBundle(getArguments());
            editMode = true;
            Picasso.get().load(bundle.getEditImgUrl()).placeholder(R.drawable.plate).into(recipeImage);
            recipeName.setText(bundle.getEditTitle());
            prepTime.setText(String.valueOf(bundle.getEditPrepTime()));
            cookTime.setText(String.valueOf(bundle.getEditCookTime()));
            instructions.setText(bundle.getEditInstructions());
            categoryId = bundle.getEditCategoryID();
            applianceId = bundle.getEditApplianceID();
            categoryBtn.setText(bundle.getEditCategoryName());
            applianceBtn.setText(bundle.getEditApplianceName());
        }



        editImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editImage();
            }
        });

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(instructions.getText().toString().isEmpty() ||cookTime.getText().toString().isEmpty() || prepTime.getText().toString().isEmpty() || recipeName.getText().toString().isEmpty() || categoryId==null || applianceId==null)
                    displayMissingError();
                else
                    saveRecipe();

            }
        });


        createViewModel.getCategoryList().observe(getViewLifecycleOwner(), new Observer<List<Category>>() {
            @Override
            public void onChanged(List<Category> recipes) {
            }
        });


        categoryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                spinner.setVisibility(View.VISIBLE);
                CategoryModel.instance.refreshGetAllCategories(new CategoryModel.RefreshGetAllCategoriesListener() {
                    @Override
                    public void onComplete() {
                        if(!categories.getValue().isEmpty())
                        {
                            AlertDialog.Builder builderSingle = new AlertDialog.Builder(getActivity());
                            builderSingle.setTitle("Select Category");

                            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.select_dialog_singlechoice);
                            for(Category cat : categories.getValue())
                                arrayAdapter.add(cat.getName());

                            builderSingle.setAdapter(arrayAdapter, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    categoryId = categories.getValue().get(which).getId();
                                    categoryBtn.setText(categories.getValue().get(which).getName());
                                }
                            });
                            builderSingle.show();
                        }
                        spinner.setVisibility(View.INVISIBLE);
                    }
                });
            }
        });

        //Appliance Button
        createViewModel.getApplianceList().observe(getViewLifecycleOwner(), new Observer<List<Appliance>>() {
            @Override
            public void onChanged(List<Appliance> recipes) {
            }
        });

        applianceBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                spinner.setVisibility(View.VISIBLE);
                ApplianceModel.instance.refreshGetAllAppliances(new ApplianceModel.RefreshGetAllAppliancesListener() {
                    @Override
                    public void onComplete() {
                        if(!appliances.getValue().isEmpty())
                        {
                            AlertDialog.Builder builderSingle = new AlertDialog.Builder(getActivity());
                            builderSingle.setTitle("Select Appliance");
                            ArrayAdapter<String> arrayAdapter2 = new ArrayAdapter<String>(getActivity(), android.R.layout.select_dialog_singlechoice);

                            for(Appliance app : appliances.getValue())
                                arrayAdapter2.add(app.getName());

                            builderSingle.setAdapter(arrayAdapter2, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    applianceId = appliances.getValue().get(which).getId();
                                    applianceBtn.setText(appliances.getValue().get(which).getName());
                                }
                            });
                            builderSingle.show();
                        }
                        spinner.setVisibility(View.INVISIBLE);
                    }
                });
            }
        });
        return root;
    }




    private void saveRecipe() {
        spinner.setVisibility(View.VISIBLE);
        final Recipe recipe= new Recipe();
        recipe.setCookTime(Integer.parseInt(cookTime.getText().toString()));
        recipe.setPrepTime(Integer.parseInt(prepTime.getText().toString()));
        recipe.setTitle(recipeName.getText().toString());
        recipe.setCategoryID(categoryId);
        recipe.setApplianceID(applianceId);
        recipe.setInstructions(instructions.getText().toString());
        recipe.setDeleted(false);
        recipe.setUserCreatorId(user.getEmail());
        if(!editMode){
            recipe.setId(this.toString() + recipe.getUserCreatorId());
        }
        else
            recipe.setId(bundle.getEditId());



        BitmapDrawable drawable = (BitmapDrawable)recipeImage.getDrawable();
        Bitmap bitmap = drawable.getBitmap();

        RecipeModel.instance.uploadImage(bitmap, recipe.getId(), new RecipeModel.UploadImageListener() {
            @Override
            public void onComplete(String url) {
                if (url == null){
                    displayFailedError();
                }else{
                    recipe.setImgURL(url);
                    RecipeModel.instance.addRecipe(recipe, new RecipeModel.AddRecipeListener() {
                        @Override
                        public void onComplete() {
                            spinner.setVisibility(View.INVISIBLE);
                            Navigation.findNavController(saveBtn).popBackStack();
                        }
                    });
                }
            }
        });
    }

    private void displayFailedError() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Operation Failed");
        builder.setMessage("Saving image failed, please try again later...");
        builder.setNeutralButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        builder.show();
    }

    private void displayMissingError() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Operation Failed");
        builder.setMessage("Fill all fields or press back to cancel");
        builder.setNeutralButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        builder.show();
    }

    private void editImage() {
        final CharSequence[] options = { "Take Photo", "Choose from Gallery","Cancel" };
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Choose your recipe picture");
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (options[item].equals("Take Photo")) {
                    Intent takePicture = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(takePicture, 0);
                } else if (options[item].equals("Choose from Gallery")) {
                    Intent pickPhoto = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(pickPhoto , 1);
                } else if (options[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode != RESULT_CANCELED) {
            switch (requestCode) {
                case 0:
                    if (resultCode == RESULT_OK && data != null) {
                        Bitmap selectedImage = (Bitmap) data.getExtras().get("data");
                        recipeImage.setImageBitmap(selectedImage);
                    }
                    break;
                case 1:
                    if (resultCode == RESULT_OK && data != null) {
                        Uri selectedImage =  data.getData();
                        String[] filePathColumn = {MediaStore.Images.Media.DATA};
                        if (selectedImage != null) {
                            Cursor cursor = getActivity().getContentResolver().query(selectedImage,
                                    filePathColumn, null, null, null);
                            if (cursor != null) {
                                cursor.moveToFirst();
                                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                                String picturePath = cursor.getString(columnIndex);
                                recipeImage.setImageBitmap(BitmapFactory.decodeFile(picturePath));
                                cursor.close();
                            }
                        }
                    }
                    break;
            }
        }
    }


    public void onResume() {
        super.onResume();
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    @Override
    public void onPause() {
        super.onPause();
        ((AppCompatActivity) getActivity()).getSupportActionBar().show();
    }

}