/*
 * Copyright (c) 2020. this code made by Dery Sudrajat
 */

package com.derysudrajat.firebasemlkittranslate

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.ml.naturallanguage.FirebaseNaturalLanguage
import com.google.firebase.ml.naturallanguage.translate.FirebaseTranslateLanguage
import com.google.firebase.ml.naturallanguage.translate.FirebaseTranslatorOptions
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnTranslate.setOnClickListener {
            if (tvSL.text == getString(R.string.indonesia)) {
                initLanguage(
                    FirebaseTranslateLanguage.ID,
                    FirebaseTranslateLanguage.EN,
                    etText.text.toString()
                )
            } else {
                initLanguage(
                    FirebaseTranslateLanguage.EN,
                    FirebaseTranslateLanguage.ID,
                    etText.text.toString()
                )
            }
        }

        // When button swap was clicked swap language
        btnSwap.setOnClickListener {
            if (tvSL.text == getString(R.string.indonesia)) {
                tvSL.text = getString(R.string.english)
                tvTL.text = getString(R.string.indonesia)
            } else {
                tvSL.text = getString(R.string.indonesia)
                tvTL.text = getString(R.string.english)
            }
        }
    }

    /**
     * Initialize FirebaseTranslatorOption with
     * SourceLanguage as @param idSL and
     * TargetLanguage as @param idTL
     * which is as FirebaseTranslatorLanguage variable
     * and text to Translate as @param text
     */
    private fun initLanguage(idSL: Any?, idTL: Any?, text: String?) {
        val option = FirebaseTranslatorOptions.Builder()
            .setSourceLanguage(idSL as Int)
            .setTargetLanguage(idTL as Int)
            .build()
        val textTranslator = FirebaseNaturalLanguage.getInstance().getTranslator(option)

        // Download model for the first time
        textTranslator.downloadModelIfNeeded()
            .addOnSuccessListener {
                Log.d("MainActivity", "Download Success")
            }
            .addOnFailureListener {
                Log.d("MainActivity", "Download Failed: $it")
            }


        // Translate text from source language to target language related with model
        textTranslator.translate(text.toString())
            .addOnSuccessListener {
                tvResult.text = it
                Log.d("MainActivity", "Translate Success $it")
            }.addOnFailureListener {
                Log.d("MainActivity", "Translate Failed: $it")
            }
    }
}

