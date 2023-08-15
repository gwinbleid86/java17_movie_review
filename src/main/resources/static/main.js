const reviewForm = document.getElementById('review_form')
const baseUrl = 'http://localhost:8089'

function reviewHandler(e) {
    e.preventDefault()
    let form = e.target
    let data = new FormData(form)

    let user = {
        email: 'qwe@qwe.qwe',
        password: 'qwe'
    }
    let movieId = document.getElementById('movieId').getAttribute('value');
    data.append('movieId', movieId)
    let headers = new Headers()
    headers.set('Authorization', 'Basic ' + btoa(user.email + ":" + user.password))
    fetch(
        baseUrl + '/reviews',
        {
            method: 'POST',
            headers: headers,
            body: data
        }
    )
}

let reviewSubmitButton = reviewForm.getElementsByTagName('button')[0]
reviewSubmitButton.addEventListener('submit', reviewHandler)

