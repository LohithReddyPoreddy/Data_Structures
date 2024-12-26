let puzzle = [];
let firstClick = null;
const puzzleGrid = document.getElementById('puzzle');
        async function startNewGame(size){
            try{   
                const response = await fetch(`http://localhost:8080/api/puzzle/start/${size}`,{
                    method: 'POST',
                    headers:{
                        'content-type':'application/json'
                    },
                    credentials:'include',
                });
                if(!response.ok){
                    throw new Error('Failed to start the game');
                }
                const result = await response.json();
                puzzle = result.puzzleBoard;
                console.log(result);
                console.log(puzzle);
                if(!Array.isArray(puzzle)){
                    console.error('Puzzle is not an array', puzzle);
                    return;
                }
                renderGame(size);
            }catch(error){
                console.error(error);
            }
        }



/*function startNewGame(size){
    puzzle = [];
    value=0;
    for(let i = 0 ;i < size ; i++){
        let values = [];
        for(let j = 0 ; j <size; j++){
            values.push(value);
            value++;
        }
        puzzle.push(values);
    }
    renderGame(size);
}*/



function renderGame(size){
    puzzleGrid.innerHTML = '';
    puzzleGrid.style.gridTemplateColumns = `repeat(${size},50px`;
    puzzle.forEach((row, rowIndex)=>{
        row.forEach((cell, colIndex)=>{
            const tile = document.createElement('div');

            if(cell !== 0){
                tile.classList.add('puzzle-piece');
            }
            else{
                tile.classList.add('empty-piece');
            }
            tile.textContent = cell === 0 ? '':cell;
            tile.addEventListener('click',()=>(handlePieceClick(rowIndex, colIndex)));
            console.log("Tile clicked")
            puzzleGrid.appendChild(tile);
        })
    })
}

async function checkValidMove(firstClick, secondClick){
    try{
        const response = await fetch('http://localhost:8080/api/puzzle/isValidMove',{
            method:'POST',
            headers:{
                'content-type':'application/json'
            },
            body: JSON.stringify({firstClick,secondClick}),
            credentials:'include',
        });
        if(!response.ok){
            throw new Error('Failed to check if it is a valid move');
        }
        else{
            const result = await response.json();
            return result;
        }
    }catch (error){
        console.error(error);
        return false;
    }
}

async function handlePieceClick(row,col) {
    if(!firstClick){
        firstClick = {row,col};
        console.log("First click", firstClick)
    }
    else{
        const secondClick = {row , col};
        console.log("Second click", secondClick)
        const isValid = await checkValidMove(firstClick,secondClick);
        console.log("Valid result", isValid);
        if(isValid){
            try{
                const response = await fetch('http://localhost:8080/api/puzzle/makeMove',{
                    method:'POST',
                    headers:{
                        'content-type':'application/json'
                    },
                    body: JSON.stringify({firstClick, secondClick}),
                    credentials:'include',
                })
                if(!response.ok){
                    throw new Error('Failed to make move');
                }
                else{
                    const result = await response.json();
                    puzzle = result.puzzleBoard;
                    renderGame(puzzle.length);
                }
            }catch(error){
                console.error(error);
            }
        }
        firstClick = null;
    }
   
    
}




const newGame3 = document.getElementById('newGame-3').addEventListener('click', ()=> startNewGame(3));
const newGame4 = document.getElementById('newGame-4').addEventListener('click', ()=> startNewGame(4));
const newGame5 = document.getElementById('newGame-5').addEventListener('click', ()=> startNewGame(5));
const newGame6 = document.getElementById('newGame-6').addEventListener('click', ()=> startNewGame(6));
const newGame7 = document.getElementById('newGame-7').addEventListener('click', ()=> startNewGame(7));
const newGame8 = document.getElementById('newGame-8').addEventListener('click', ()=> startNewGame(8));

