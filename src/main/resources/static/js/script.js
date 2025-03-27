
$(document).ready(function() {

    $(document).on('submit', '.updateForm', function(event) {
        event.preventDefault();

        var form = $(this);
        var url = form.attr('action');
        var data = form.serialize();
        var csrfToken = $('input[name="_csrf"]').val();
        var csrfHeader = $('meta[name="_csrf_header"]').attr('content');

        $.ajax({
            type: 'POST',
            url: url,
            data: data,
            beforeSend: function(xhr) {
                xhr.setRequestHeader(csrfHeader, csrfToken);
            },
            success: function(response) {
                $('#bookContainer').replaceWith($(response).filter('#bookContainer'));

                setTimeout(function(){
                    $('.alert-success').fadeOut('slow', function(){
                        $(this).remove();
                    });
                }, 200);
            }
        });
    });

    $(document).on('submit', '.updateUserBookForm', function(event) {
        event.preventDefault();

        var form = $(this);
        var url = form.attr('action');
        var data = form.serialize();
        var csrfToken = $('input[name="_csrf"]').val();
        var csrfHeader = $('meta[name="_csrf_header"]').attr('content');

        $.ajax({
            type: 'POST',
            url: url,
            data: data,
            beforeSend: function(xhr) {
                xhr.setRequestHeader(csrfHeader, csrfToken);
            },
            success: function(response) {
                $('#userBookContainer').replaceWith($(response).filter('#userBookContainer'));

                setTimeout(function(){
                    $('.alert-success').fadeOut('slow', function(){
                        $(this).remove();
                    });
                }, 200);
            }
        });
    });

    $('#my-books-tab').click(function(event) {
        event.preventDefault();

        var csrfToken = $('input[name="_csrf"]').val();
        var csrfHeader = $('meta[name="_csrf_header"]').attr('content');

        $.ajax({
            type: 'GET',
            url: '/library/getMyBooks',
            beforeSend: function(xhr) {
                xhr.setRequestHeader(csrfHeader, csrfToken);
            },
            success: function(response) {
                $('#userBookContainer').replaceWith($(response).filter('#userBookContainer'));
            },
            error: function(error) {
                console.error("Eroare AJAX:", error);
            }
        });
    });

    $('#all-books-tab').click(function(event) {
        event.preventDefault();

        var csrfToken = $('input[name="_csrf"]').val();
        var csrfHeader = $('meta[name="_csrf_header"]').attr('content');

        $.ajax({
            type: 'GET',
            url: '/library/refresh',
            beforeSend: function(xhr) {
                xhr.setRequestHeader(csrfHeader, csrfToken);
            },
            success: function(response) {
                $('#bookContainer').replaceWith($(response).filter('#bookContainer'));
            },
            error: function(error) {
                console.error("Eroare AJAX:", error);
            }
        });
    });

    $('#create-book-tab').click(function(event) {
        event.preventDefault();

        var csrfToken = $('input[name="_csrf"]').val();
        var csrfHeader = $('meta[name="_csrf_header"]').attr('content');

        $.ajax({
            type: 'GET',
            url: '/library/createBook',
            beforeSend: function(xhr) {
                xhr.setRequestHeader(csrfHeader, csrfToken);
            },
            success: function(response) {
                $('#createBookContainer').replaceWith($(response).filter('#createBookContainer'));
            },
            error: function(error) {
                console.error("Eroare AJAX:", error);
            }
        });
    });

    if (localStorage.getItem("bookCreated") === "true") {

        let alertContainer = $('<div>', { class: 'alert-container' });
        let successMessageDiv = $('<div>', {
            class: 'message alert alert-success',
            text: 'Book was successfully created!'
        });

        alertContainer.append(successMessageDiv);
        $('body').append(alertContainer);

        localStorage.removeItem("bookCreated");

        setTimeout(() => {
            alertContainer.remove();
        }, 1000);
    }
});

function toggleChevron(element) {
    let chevronUp = $(element).find('.chevron-up');
    let chevronDown = $(element).find('.chevron-down');

    if (chevronUp.is(':hidden')) {
        chevronUp.show();
        chevronDown.hide();
    } else {
        chevronUp.hide();
        chevronDown.show();
    }
}

function toggleEdit(bookId) {
    $("#bookDetails-" + bookId).hide();
    $("#editForm-" + bookId).show();
    $("#editBtn-" + bookId).hide();
}

function saveBook(bookId) {
    var csrfToken = $('input[name="_csrf"]').val();
    var csrfHeader = $('meta[name="_csrf_header"]').attr('content');

    let updatedBook = {
        title: $("#title-" + bookId).val(),
        author: $("#author-" + bookId).val(),
        description: $("#description-" + bookId).val()
    };

    $.ajax({
        url: `/book/${bookId}`,
        type: "PUT",
        contentType: "application/json",
        data: JSON.stringify(updatedBook),
        beforeSend: function(xhr) {
            xhr.setRequestHeader(csrfHeader, csrfToken);
        },
        success: function(response) {
            // Update the book details with new values
            $("#bookDetails-" + bookId).html(`
                <p class="book-title">${response.title}</p>
                <p class="book-author">by ${response.author}</p>
                <p class="book-description">${response.description}</p>
            `);

            // Hide edit form and show updated details
            $("#editForm-" + bookId).hide();
            $("#bookDetails-" + bookId).show();
            $("#editBtn-" + bookId).show();
        },
        error: function(xhr, status, error) {
            console.error("Error updating book:", xhr.responseText);
        }
    });
}

function createBook() {
    var csrfToken = $('input[name="_csrf"]').val();
    var csrfHeader = $('meta[name="_csrf_header"]').attr('content');

    let newBook = {
        title: $("#title").val(),
        author: $("#author").val(),
        description: $("#description").val()
    };

    $.ajax({
        url: "/book/create",
        type: "POST",
        contentType: "application/json",
        data: JSON.stringify(newBook),
        beforeSend: function(xhr) {
            xhr.setRequestHeader(csrfHeader, csrfToken);
        },
        success: function(response) {
            $("#title").val("");
            $("#author").val("");
            $("#description").val("");
            localStorage.setItem("bookCreated", "true");
            location.reload();
        },
        error: function(xhr, status, error) {
            console.error("Error creating book:", xhr.responseText);
        }
    });
}