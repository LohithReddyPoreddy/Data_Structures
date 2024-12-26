package com.arrays.sliiding_puzzle.controller;


import com.arrays.sliiding_puzzle.model.Move;
import com.arrays.sliiding_puzzle.model.PuzzleModel;
import com.arrays.sliiding_puzzle.service.PuzzleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/puzzle")
@CrossOrigin(origins = "http://127.0.0.1:5500", allowCredentials = "true")
public class PuzzleController {

    @Autowired
    private PuzzleService puzzleService;


    @PostMapping("/start/{size}")
    public ResponseEntity<PuzzleModel> createNewGame(@PathVariable int size){
        PuzzleModel game = puzzleService.createNewGame(size);
        return ResponseEntity.ok(game);
    }

    @PostMapping("/isValidMove")
    public ResponseEntity<?> isValidMove(@RequestBody Move move){
        try{
            boolean isVlaid = puzzleService.isValidMove(move);
            return ResponseEntity.ok(isVlaid);
        }catch (Exception e){
            return ResponseEntity.internalServerError()
                    .body("Error checking move validity: " + e.getMessage());
        }
    }

    @PostMapping("/makeMove")
    public ResponseEntity<?> makeMove(@RequestBody Move move){
        try{
            PuzzleModel updateModel = puzzleService.makeMove(move);
            return ResponseEntity.ok(updateModel);
        }catch (Exception e){
            return ResponseEntity.internalServerError()
                    .body("Error making move: " + e.getMessage());
        }
    }


}
