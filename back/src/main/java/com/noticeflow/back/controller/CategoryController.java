package com.noticeflow.back.controller;

import com.noticeflow.back.domain.User;
import com.noticeflow.back.dto.ApiResponse;
import com.noticeflow.back.dto.CategoryRequest;
import com.noticeflow.back.dto.CategoryResponse;
import com.noticeflow.back.service.CategoryService;
import com.noticeflow.back.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "카테고리 API", description = "공지사항 카테고리 관리 API")
@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;
    private final UserService userService;

    @Operation(
            summary = "카테고리 생성",
            description = "새로운 카테고리를 생성합니다. 기관 관리자만 사용 가능합니다.",
            security = @SecurityRequirement(name = "Bearer Authentication")
    )
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "200",
                    description = "성공",
                    content = @Content(schema = @Schema(implementation = CategoryResponse.class))
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "400",
                    description = "잘못된 요청"
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "401",
                    description = "인증 실패"
            )
    })
    @PostMapping
    public ResponseEntity<ApiResponse<CategoryResponse>> createCategory(
            Authentication authentication,
            @Valid @RequestBody CategoryRequest request) {
        Long userId = (Long) authentication.getPrincipal();
        User user = userService.getUserEntityById(userId);

        CategoryResponse category = categoryService.createCategory(user, request);
        return ResponseEntity.ok(ApiResponse.success("카테고리가 생성되었습니다.", category));
    }

    @Operation(
            summary = "카테고리 목록 조회",
            description = "기관의 카테고리 목록을 조회합니다. includeHidden=true로 설정하면 숨겨진 카테고리도 조회됩니다.",
            security = @SecurityRequirement(name = "Bearer Authentication")
    )
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "200",
                    description = "성공",
                    content = @Content(schema = @Schema(implementation = CategoryResponse.class))
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "401",
                    description = "인증 실패"
            )
    })
    @GetMapping
    public ResponseEntity<ApiResponse<List<CategoryResponse>>> getCategories(
            Authentication authentication,
            @RequestParam(defaultValue = "false") boolean includeHidden) {
        Long userId = (Long) authentication.getPrincipal();
        User user = userService.getUserEntityById(userId);

        List<CategoryResponse> categories = categoryService.getCategories(user, includeHidden);
        return ResponseEntity.ok(ApiResponse.success(categories));
    }

    @Operation(
            summary = "카테고리 상세 조회",
            description = "특정 카테고리의 상세 정보를 조회합니다.",
            security = @SecurityRequirement(name = "Bearer Authentication")
    )
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "200",
                    description = "성공",
                    content = @Content(schema = @Schema(implementation = CategoryResponse.class))
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "401",
                    description = "인증 실패"
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "404",
                    description = "카테고리를 찾을 수 없음"
            )
    })
    @GetMapping("/{categoryId}")
    public ResponseEntity<ApiResponse<CategoryResponse>> getCategory(
            Authentication authentication,
            @PathVariable Long categoryId) {
        Long userId = (Long) authentication.getPrincipal();
        User user = userService.getUserEntityById(userId);

        CategoryResponse category = categoryService.getCategory(user, categoryId);
        return ResponseEntity.ok(ApiResponse.success(category));
    }

    @Operation(
            summary = "카테고리 수정",
            description = "카테고리 정보를 수정합니다. 기관 관리자만 사용 가능합니다.",
            security = @SecurityRequirement(name = "Bearer Authentication")
    )
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "200",
                    description = "성공",
                    content = @Content(schema = @Schema(implementation = CategoryResponse.class))
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "400",
                    description = "잘못된 요청"
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "401",
                    description = "인증 실패"
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "404",
                    description = "카테고리를 찾을 수 없음"
            )
    })
    @PutMapping("/{categoryId}")
    public ResponseEntity<ApiResponse<CategoryResponse>> updateCategory(
            Authentication authentication,
            @PathVariable Long categoryId,
            @Valid @RequestBody CategoryRequest request) {
        Long userId = (Long) authentication.getPrincipal();
        User user = userService.getUserEntityById(userId);

        CategoryResponse category = categoryService.updateCategory(user, categoryId, request);
        return ResponseEntity.ok(ApiResponse.success("카테고리가 수정되었습니다.", category));
    }

    @Operation(
            summary = "카테고리 숨기기",
            description = "카테고리를 숨깁니다. 해당 카테고리를 사용하는 공지사항이 있으면 숨길 수 없습니다.",
            security = @SecurityRequirement(name = "Bearer Authentication")
    )
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "200",
                    description = "성공"
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "400",
                    description = "해당 카테고리를 사용하는 공지사항이 있음"
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "401",
                    description = "인증 실패"
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "404",
                    description = "카테고리를 찾을 수 없음"
            )
    })
    @PatchMapping("/{categoryId}/hide")
    public ResponseEntity<ApiResponse<Void>> hideCategory(
            Authentication authentication,
            @PathVariable Long categoryId) {
        Long userId = (Long) authentication.getPrincipal();
        User user = userService.getUserEntityById(userId);

        categoryService.hideCategory(user, categoryId);
        return ResponseEntity.ok(ApiResponse.success("카테고리가 숨겨졌습니다.", null));
    }

    @Operation(
            summary = "카테고리 삭제",
            description = "카테고리를 삭제합니다. 해당 카테고리를 사용하는 공지사항이 있으면 삭제할 수 없습니다.",
            security = @SecurityRequirement(name = "Bearer Authentication")
    )
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "200",
                    description = "성공"
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "400",
                    description = "해당 카테고리를 사용하는 공지사항이 있음"
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "401",
                    description = "인증 실패"
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "404",
                    description = "카테고리를 찾을 수 없음"
            )
    })
    @DeleteMapping("/{categoryId}")
    public ResponseEntity<ApiResponse<Void>> deleteCategory(
            Authentication authentication,
            @PathVariable Long categoryId) {
        Long userId = (Long) authentication.getPrincipal();
        User user = userService.getUserEntityById(userId);

        categoryService.deleteCategory(user, categoryId);
        return ResponseEntity.ok(ApiResponse.success("카테고리가 삭제되었습니다.", null));
    }
}
