<#import "../layout.ftlh" as main>
<@main.layout>
<#--    <div class="row">-->
    <div class="card" style="width: 18rem;">
        <div class="card-body">
            <input type="hidden" value="${movie.id}" id="movieId">
            <h5 class="card-title">${movie.name}</h5>
            <h6 class="card-subtitle mb-2 text-muted">${movie.director.fullName}</h6>
            <h6 class="card-subtitle mb-2 text-muted">${movie.releaseYear?string.number}</h6>
            <p class="card-text">${movie.description}</p>
            <hr>
            <h6 class="card-subtitle mb-2 text-muted">Cast:</h6>
            <#list movie.castMembers as item>
                <p class="card-text">${item.fullName} - ${item.role}</p>
            </#list>
        </div>
    </div>
<#--    </div>-->

    <hr>

    <div id="review_block">
        <form id="review_form">
            <div class="form-group">
                <label for="rating">Rating</label>
                <select class="form-control" id="rating" name="rating">
                    <option>1</option>
                    <option>2</option>
                    <option>3</option>
                    <option>4</option>
                    <option>5</option>
                </select>
            </div>
            <div class="form-group">
                <label for="comment">Comment</label>
                <textarea class="form-control" id="comment" rows="3" name="comment"></textarea>
            </div>
            <button type="submit" class="btn btn-success">Success</button>
        </form>
    </div>
    <script>
        const reviewForm = document.getElementById('review_form')
        const baseUrl = 'http://localhost:8089'
        const movieId = document.getElementById('movieId').getAttribute('value');

        const user = {
            email: 'qwe@qwe.qwe',
            password: 'qwe'
        }
        const headers = new Headers()
        headers.set('Authorization', 'Basic ' + btoa(user.email + ":" + user.password))
        headers.set("Content-Type", "application/json")

        function reviewHandler(e) {
            e.preventDefault()
            let form = e.target
            let data = new FormData(form)


            data.append('movieId', movieId)


            let json = JSON.stringify(Object.fromEntries(data))

            fetch(
                baseUrl + '/reviews',
                {
                    method: 'POST',
                    headers: headers,
                    body: json
                }
            )
        }

        reviewForm.addEventListener('submit', reviewHandler)


        window.addEventListener('load', async () => {

            setInterval(async () => {
                let reviewBlock = document.getElementById('review_block')
                let response = JSON.stringify(await getComments())
                let review = JSON.parse(response)
                console.log(review)
                for (let reviewElement of review) {
                    console.log(reviewElement)
                    let element1 = document.createElement('p')
                    let element2 = document.createElement('p')
                    let element3 = document.createElement('p')
                    element1.innerText = reviewElement.reviewer
                    element2.innerText = reviewElement.rating
                    element3.innerText = reviewElement.comment
                    reviewBlock.append(element1)
                    reviewBlock.append(element2)
                    reviewBlock.append(element3)
                }
            })
        })

        async function getComments() {
            let response = await fetch(
                baseUrl + `/reviews/${movieId}`,
                {
                    method: 'GET',
                    headers: headers
                }
            )
            return await response.json()
        }
    </script>
</@main.layout>